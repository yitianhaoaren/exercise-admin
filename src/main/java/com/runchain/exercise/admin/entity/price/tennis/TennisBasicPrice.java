package com.runchain.exercise.admin.entity.price.tennis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.runchain.exercise.admin.entity.price.PriceEntity;

/**
 * 价格实体：网球-非周末
 *
 * @author HenryYan
 *
 */
@Entity
@Table(name = "t_field_tennis_basic_price")
public class TennisBasicPrice extends PriceEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public TennisBasicPrice() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}