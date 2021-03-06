package com.runchain.exercise.admin.web.field;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.runchain.arch.web.base.CrudActionSupport;
import com.runchain.arch.web.base.JqGridCrudActionSupport;
import com.runchain.exercise.admin.butil.venue.VenueInfoUtil;
import com.runchain.exercise.admin.entity.field.Tennis;
import com.runchain.exercise.admin.entity.venue.VenueInfo;
import com.runchain.exercise.admin.service.field.tennis.TennisManager;
import com.runchain.arch.util.orm.PropertyFilterUtils;

/**
 * 网球场地Action
 *
 * @author HenryYan
 *
 */
@Namespace("/field")
@Action(value = "tennis")
@Results( { @Result(name = CrudActionSupport.JSON, type = CrudActionSupport.JSON) })
public class TennisAction extends JqGridCrudActionSupport<Tennis> {

	private static final long serialVersionUID = 1L;
	
	@Resource TennisManager manager;

	@Override
	public String delete() {
		try {
			manager.deleteField(id);
		} catch (Exception e) {
			logger.error("删除[网球]场地时", e);
		}
		return null;
	}

	@Override
	public String list() {
		try {
			VenueInfo venueInfo = VenueInfoUtil.getVenueFromSession();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());
			PropertyFilterUtils.handleFilter(page, Tennis.class, filters);
			
			filters.add(new PropertyFilter("EQL_venueId", venueInfo.getId().toString()));
			
			page = manager.searchField(page, filters);
		} catch (Exception e) {
			logger.error("读取[网球]场地列表：", e);
		}
		return JSON;
	}
	
	@Override
	protected void prepareModel() {
		if (id != null) {
			entity = manager.getField(id);
		} else {
			entity = new Tennis();
			VenueInfo venueInfo = VenueInfoUtil.getVenueFromSession();
			entity.setVenueId(venueInfo.getId());
			entity.setCreateDate(new Date());
		}
	}

	@Override
	public String save() {
		try {
			Boolean batch = new Boolean(StringUtils.defaultIfEmpty(Struts2Utils.getParameter("batch"), "false"));
			
			// 批量添加
			if (batch) {
				try {
					String strNumber = Struts2Utils.getParameter("number");
					if (StringUtils.isEmpty(strNumber) || strNumber.equals("0")) {
						Struts2Utils.renderText("请填写数量");
						return null;
					}
					
					manager.addBatchField(entity, new Integer(strNumber));
					Struts2Utils.renderText("true");
				} catch (Exception e) {
					logger.error("批量保存[网球]场地：", e);
				}
			} else {
				manager.saveField(entity);
			}
			
		} catch (Exception e) {
			logger.error("保存[网球]：", e);
		}
		return null;
	}

	@Override
	public Page<Tennis> getPage() {
		return page;
	}
	
	public Tennis getModel() {
		return entity;
	}

}
