package com.training.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.training.formbean.BackActionForm;
import com.training.model.Goods;
import com.training.service.BackendService;
import com.training.vo.PageSearchKey;
import com.training.vo.Pagination;
import com.training.vo.SalesReport;

import net.sf.json.JSONObject;

public class BackendActionAjax extends DispatchAction{
private BackendService backendservice = BackendService.getInstance();
	
	public ActionForward queryGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();	
		session.removeAttribute("goods");
		BackActionForm backactionform=(BackActionForm)form;
		PageSearchKey pagesearchkey=new PageSearchKey();
		BeanUtils.copyProperties(pagesearchkey, backactionform);
		Pagination pages=backendservice.pagInation(pagesearchkey);
		req.setAttribute("pagesearchkey", pagesearchkey);//搜尋資訊
		req.setAttribute("pages", pages);//分頁
		req.setAttribute("goods", pages.getGoodsList());//分頁商品列表
		
		return mapping.findForward("backendGoodsList");
	}
	//for Ajax
	public ActionForward getupdateGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String goodsId=req.getParameter("goodsID");//被選中的id
		Goods good = backendservice.queryGoodsById(goodsId);
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(JSONObject.fromObject(good));
		out.flush();
		out.close();
		
		return null;//非同步不需倒回頁面
	}

	public ActionForward updateGoodsview(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Goods> goods = backendservice.queryGoods();
		req.setAttribute("goods", goods);
		
		String id = req.getParameter("goodsID");
		id = (id != null) ? id : (String)req.getSession().getAttribute("updateGoodsID");
		if(id != null){
			Goods good = backendservice.queryGoodsById(id);//將以更新資料重新查詢
			req.setAttribute("updategoods", good);
		}
		return mapping.findForward("backendGoodsReplenishmentview");
	}

	public ActionForward updateGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		BackActionForm backactionform=(BackActionForm)form;
		Goods good = new Goods();	
		BeanUtils.copyProperties(good, backactionform);
		boolean modifyResult = backendservice.modifyGood(good);
		String message = modifyResult ? "商品資料更新成功" : "商品資料更新失敗2";
		session.setAttribute("updateMsg", message);
		session.setAttribute("updateGoodsID", good.getGoodsID());//給前端判斷已選中的商品的資訊
		// Redirect to view
		return mapping.findForward("backendGoodsReplenishment");
	}
	public ActionForward addGoodsview(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		return mapping.findForward("backendGoodsCreateview");
	}
	public ActionForward addGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		try {
			BackActionForm backactionform=(BackActionForm)form;
			Goods good = new Goods();
			BeanUtils.copyProperties(good, backactionform);
			FormFile file = backactionform.getGoodsImage();
			good.setGoodsImageName(file.getFileName());
			ServletContext application=this.getServlet().getServletContext();
			String goodsImgPath=this.servlet.getInitParameter("GoodsImgPath");
			String serverGoodsImgPath =application.getRealPath(goodsImgPath);
			Path serverImgPath = Paths.get(serverGoodsImgPath).resolve(file.getFileName());
			try (InputStream fileContent =file.getInputStream() ;){
		        Files.copy(fileContent, serverImgPath, StandardCopyOption.REPLACE_EXISTING);
		    }
		boolean createResult = backendservice.createGood(good);
		String message = createResult ? "商品新增成功" : "商品新增失敗";
		session.setAttribute("createMsg",message );
		}catch (Exception e) {
			e.printStackTrace();
		}
		// Redirect to view
		return mapping.findForward("backendGoodsCreate");
	}

	public ActionForward querySalesReport(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BackActionForm backactionform=(BackActionForm)form;
		String queryStartDate = backactionform.getQueryStartDate();
		String queryEndDate = backactionform.getQueryEndDate();
		Set<SalesReport> salesreport = backendservice.querySalesReport(queryStartDate,queryEndDate);
		req.setAttribute("salesreport", salesreport);
		salesreport.stream().forEach(a -> System.out.println(a.toString()));
		
		return mapping.findForward("backendGoodsSaleReport");
	}

}
