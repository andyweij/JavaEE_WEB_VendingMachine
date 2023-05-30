package com.training.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.MemberDao;
import com.training.model.Member;

public class LoginAction extends DispatchAction {
	
	private static MemberDao memberDao = MemberDao.getInstance();
	
	/**
	 * info:這是負責"登入"的action method
	 * @param mapping
	 * @param form
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
    public ActionForward login(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 登入請求
    	ActionForward actFwd = null;
    	HttpSession session = req.getSession();
    	String inputID = req.getParameter("id");
        String inputPwd = req.getParameter("pwd");
        String loginMsg = null;
        // Step2:依使用者所輸入的帳戶名稱取得 Member
        Member member= memberDao.ByIdentificationNo(inputID);
    	if(member != null) {
    		// Step3:取得帳戶後進行帳號、密碼比對
    		String id = member.getIdentificationNo();    		
    		String pwd = member.getPassword();
    		if(id.equals(inputID) && pwd.equals(inputPwd)) {
    			loginMsg= member.getCustomerName()+" 先生/小姐您好!";
    			// 將account存入session scope 以供LoginCheckFilter之後使用!
    			session.setAttribute("member", member);
    			actFwd = mapping.findForward("success");        			
    		} else {
                // Step4:帳號、密碼錯誤,轉向到 "/BankLogin.html" 要求重新登入.
    			loginMsg = "帳號或密碼錯誤";
    			actFwd = mapping.findForward("fail");
    		}
    	} else {
            // Step5:無此帳戶名稱,轉向到 "/BankLogin.html" 要求重新登入.
    		loginMsg = "無此帳戶名稱,請重新輸入!";        		
    		actFwd = mapping.findForward("fail");
    	}
    	req.setAttribute("loginMsg", loginMsg);
    	return actFwd;
    }
    
    /**
     * info:這是負責"登出"的action method
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward logout(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 登出請求
    	HttpSession session = request.getSession();
//		session.removeAttribute("account");//移除登入資訊
		session.invalidate();//清空session
		request.setAttribute("loginMsg", "謝謝您的光臨!");
    	
    	return mapping.findForward("fail");
    }
    public ActionForward register(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	ActionForward actFwd = null;
    	String inputID = req.getParameter("newid");
        String inputPwd = req.getParameter("newpwd");
        String inputuserName=req.getParameter("newname");
        String loginMsg = null;
        Member member= memberDao.ByIdentificationNo(inputID);
        if(member!=null) {
        	loginMsg="帳戶已存在";
        	actFwd = mapping.findForward("crtfail"); 
        }else {
        	member=new Member();
        	member.setIdentificationNo(inputID);
        	member.setPassword(inputPwd);
        	member.setCustomerName(inputuserName);
        	boolean registerresult=memberDao.registerAccount(member);
        	loginMsg=registerresult ? "帳戶註冊成功" : "帳戶註冊失敗";
        	actFwd = mapping.findForward("crtsuccess");
        }
        
    	req.setAttribute("loginMsg", loginMsg);
    	return actFwd;
    }
}
