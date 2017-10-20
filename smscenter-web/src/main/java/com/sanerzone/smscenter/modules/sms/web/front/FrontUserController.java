/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web.front;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.utils.IdGen;
import com.sanerzone.jeebase.common.utils.JedisUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.entity.Dict;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.DictUtils;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.common.message.Result;
import com.sanerzone.smscenter.common.utils.AccountCacheUtils;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.entity.BasePayLogs;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoice;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoiceLogs;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;
import com.sanerzone.smscenter.modules.account.service.BasePayLogsService;
import com.sanerzone.smscenter.modules.account.service.BaseUserInvoiceLogsService;
import com.sanerzone.smscenter.modules.account.service.BaseUserInvoiceService;

/**
 * 用户信息Controller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "/front/user")
public class FrontUserController extends BaseController {
    
    @Autowired
    private BaseAccountService baseAccountService;
    
    @Autowired
    private BaseUserInvoiceService baseUserInvoiceService;
    
    @Autowired
    private BaseUserInvoiceLogsService baseUserInvoiceLogsService;
    
    @Autowired
    private BasePayLogsService basePayLogsService;
	
	//获取用户基本信息
	@RequestMapping(value = "userInfo")
	public String userInfo(HttpServletResponse response) {
		Result<User> result = new Result<User>();
		User user = UserUtils.getUser();
		BaseAccount param = new BaseAccount();
		param.setUserid(user.getId());
		BaseAccount baseAccount = baseAccountService.getByUserid(param);
		if(baseAccount != null){
			Map<String,Object> account = baseAccountService.getDBMap(baseAccount.getId());
			user.setAccount(account);
		}
		user.setCompanyName(user.getCompany().getName());
		result.setData(user);
		return renderString(response, result);
	}
	
	//用户余额
	@RequestMapping(value = "balance")
	public String balance(HttpServletResponse response) {
		Result<String> result = new Result<String>();
		User user = UserUtils.getUser();
        String amountKey = AccountCacheUtils.getAmountKey("amount", "sms", user.getId());
        String money = JedisUtils.get(amountKey);//余额
        if(StringUtils.isBlank(money)){
        	money = "0";
        }
        result.setData(money);
        return renderString(response, result);
	}
	
	//用户充值
	@RequestMapping(value = "recharge")
	public String recharge(BasePayLogs basePayLogs, double credit, HttpServletRequest request, HttpServletResponse response, Model model) {
		int money = (int)(credit*100);//转换成分
		basePayLogs.setUserid(UserUtils.getUser().getId());//用户ID
		basePayLogs.setMoney(money);
		basePayLogs.setPayType("1");//网上支付
		basePayLogs.setPayid(IdGen.uuid());
		basePayLogs.setOrderStatus("-1");//未支付
		basePayLogsService.save(basePayLogs);
		model.addAttribute("item", basePayLogs);
	    return "modules/sms/front/order";
	}
	
	//充值明细
	@RequestMapping(value = "orderDetail")
	public String orderDetail(BasePayLogs basePayLogs, Model model) {
		basePayLogs = basePayLogsService.getByParam(basePayLogs);
		model.addAttribute("item", basePayLogs);
	    return "modules/sms/front/order";
	}
	
	//充值成功后回调
	@RequestMapping(value = "callBackByPay")
	public String callBackByPay(BasePayLogs basePayLogs, Model model) {
		BasePayLogs param = new BasePayLogs();
		param.setOrderStatus("1");
		param.setPayid(basePayLogs.getPayid());
		int count = basePayLogsService.updateStatus(param);//修改状态
		if(count > 0){
			//充值条数
			basePayLogs = basePayLogsService.getByParam(basePayLogs);
			String userid = basePayLogs.getUserid();
			int amount = rechargeAmount(basePayLogs.getMoney());
			baseAccountService.rechargeAmount("0", userid, "CZ00", String.valueOf(amount), "sms", 
					"10", basePayLogs.getPayMent(), "用户ID:"+userid+"，自助充值", "1", "", basePayLogs.getPayid(), false);
			
			String key = AccountCacheUtils.getAmountKey("sms", userid);
			JedisUtils.incrBy(key, amount);
			//增加发票
			BaseUserInvoice baseUserInvoice = new BaseUserInvoice();
			baseUserInvoice.setUserid(userid);
			baseUserInvoice.setAmount(Long.valueOf(basePayLogs.getMoney()));
			baseUserInvoice.setFreezeAmount(0L);
			baseUserInvoice.setTotalAmount(0L);
			baseUserInvoiceService.invoiceHandle(baseUserInvoice);
		}
		
	    return "modules/sms/front/payNow";
	}
	
	//获取充值条数
	private int rechargeAmount(int money){
		int count = 0;
		List<Dict> list = DictUtils.getDictList("money_level");
		String price = "6";//6分一条
		if(list != null && list.size() > 0){
			for (Dict dict : list) {
				int label = Integer.valueOf(dict.getLabel());
				if(money >=label){
					price = dict.getValue();
					break;
				}
			}
		}
		double dM = Double.valueOf(money);
		double dP = Double.valueOf(price);

		count = (int)(dM%dP == 0 ? dM/dP : 1+dM/dP);
		return count;
		
	}
	
	//用户充值纪录列表
	@RequestMapping(value = "rechargeList")
	public String rechargeList(BasePayLogs basePayLogs, HttpServletRequest request, HttpServletResponse response,Model model) {
		basePayLogs.setUserid(UserUtils.getUser().getId());
		Page<BasePayLogs> page = basePayLogsService.findPage(new Page<BasePayLogs>(request, response), basePayLogs);
		model.addAttribute("page", page);
	    return "modules/sms/front/rechargeList";
	}
	
	//索取发票
	@RequestMapping(value = "applyForInvoice")
	public String applyForInvoice(BaseUserInvoiceLogs baseUserInvoiceLogs, double edAmount, HttpServletRequest request, HttpServletResponse response) {
		Result<String> result = new Result<String>();
		try{
			User user = UserUtils.getUser();//用户
			BaseUserInvoice baseUserInvoice = baseUserInvoiceService.get(user.getId());
			if(baseUserInvoice == null){
				result.setCode("1");
				result.setMsg("用户不存在发票信息");
				return renderString(response, result);
			}
			
			Long money = Double.valueOf(edAmount*100).longValue();
			baseUserInvoiceLogs.setAmount(money);
			if(!invoiceFlag(baseUserInvoice.getAmount(), money)){
				result.setCode("2");
				result.setMsg("开具发票金额大于可索取发票最大金额");
				return renderString(response, result);
			}
			baseUserInvoiceLogs.setUserid(user.getId());
			baseUserInvoiceLogs.setStatus("-1");//索取发票
			baseUserInvoiceLogsService.save(baseUserInvoiceLogs);
			baseUserInvoiceLogsService.invoiceHandle("-1", money, user.getId());
		}catch(Exception e){
			e.printStackTrace();
			result.setCode("1");
		}
		
	    return renderString(response, result);
	}
	
	//开票记录
	@RequestMapping(value = "invoiceList")
	public String invoiceList(BaseUserInvoiceLogs baseUserInvoiceLogs, HttpServletRequest request, HttpServletResponse response) {
		Result<Page<BaseUserInvoiceLogs>> result = new Result<Page<BaseUserInvoiceLogs>>();
		User user = UserUtils.getUser();//用户
		baseUserInvoiceLogs.setUserid(user.getId());
		
		Page<BaseUserInvoiceLogs> pageParam = new Page<BaseUserInvoiceLogs>(request, response);
		pageParam.setOrderBy(" a.create_time DESC");
		Page<BaseUserInvoiceLogs> page = baseUserInvoiceLogsService.findPage(pageParam, baseUserInvoiceLogs); 
		result.setData(page);
	    return renderString(response, result);
	}
	
	
	private boolean invoiceFlag(Long maxAmount,Long amount){
		if(maxAmount >= amount)return true;
		return false;
	}
	
	//获取当前用户可开票余额
	@RequestMapping(value="getAmount")
	public String getUserAmount(BaseUserInvoice baseUserInvoice,HttpServletResponse response){
		Result<String> result = new Result<String>();
		User user = UserUtils.getUser();//用户
		baseUserInvoice.setUserid(user.getId());
		String amount=baseUserInvoiceService.getUserAmount(baseUserInvoice);
		result.setData(amount);
		return renderString(response, result);
	}
	
	
}