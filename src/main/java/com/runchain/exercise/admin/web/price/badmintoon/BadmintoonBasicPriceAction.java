package com.runchain.exercise.admin.web.price.badmintoon;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;
import com.runchain.exercise.admin.butil.venue.VenueInfoUtil;
import com.runchain.exercise.admin.entity.price.badmintoon.BadmintoonBasicPrice;
import com.runchain.exercise.admin.entity.venue.VenueInfo;
import com.runchain.exercise.admin.service.price.IPriceManager;
import com.runchain.arch.util.json.JSONUtil;

/**
 * 羽毛球-非周末Action
 *
 * @author HenryYan
 *
 */
@Namespace("/price")
@Action("badmintoon-basic")
@Results( { @Result(location = "price-basic.jsp") })
public class BadmintoonBasicPriceAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "badmintoonBasicPriceManager")
	protected IPriceManager<BadmintoonBasicPrice> manager;
	private Long id;
	private String fieldType = "badmintoon";
	private String priceType = "basic";
	private List<BadmintoonBasicPrice> prices;

	//-- 业务方法 --//
	public String delete() throws Exception {
		manager.deletePrice(id);
		return null;
	}

	/**
	 * 批量添加价格
	 * @return
	 */
	public String batchAdd() {
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			VenueInfo venueInfo = VenueInfoUtil.getVenueFromSession();
			Long venueId = venueInfo.getId();
			JSONObject jsonObject = JSONUtil.readJson(request);
			jsonObject.accumulate("venueId", String.valueOf(venueId));
			manager.batchAdd(venueId, jsonObject);
			Struts2Utils.renderText("true");
		} catch (Exception e) {
			logger.error("批量添加[羽毛球][非周末]价格时：", e);
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 更新价格
	 * @return
	 */
	public String updatePrice() {
		String strId = Struts2Utils.getParameter("id");
		String strPrice = Struts2Utils.getParameter("price");
		String priceType = Struts2Utils.getParameter("priceType");
		if (StringUtils.isNotEmpty(strId) && StringUtils.isNotEmpty(strPrice) 
				&& StringUtils.isNotEmpty(priceType)) {
			BadmintoonBasicPrice price = manager.getPrice(new Long(strId));
			if ("basic".equals(priceType)) {
				price.setPrice(new Integer(strPrice));
			} else if("protocol".equals(priceType)) {
				price.setPaymentCommision(new Float(strPrice));
			}
			manager.savePrice(price);
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	public String list() {
		try {
			VenueInfo venueInfo = VenueInfoUtil.getVenueFromSession();
			prices = manager.findAllPrices(venueInfo.getId());
		} catch (Exception e) {
			logger.error("获取[羽毛球][非周末]价格列表时：", e);
		}
		return SUCCESS;
	}

	//-- getter and setter --//
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<BadmintoonBasicPrice> getPrices() {
		return prices;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getPriceType() {
		return priceType;
	}

}
