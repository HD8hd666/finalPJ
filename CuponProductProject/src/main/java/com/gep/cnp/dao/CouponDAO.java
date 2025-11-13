package com.gep.cnp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gep.cnp.model.Coupon;
import com.jep.cnp.util.ConnectionUtil;

public class CouponDAO {
	
	public void save(Coupon coupon) {
		try (Connection connection = ConnectionUtil.getConnection();
		     PreparedStatement statement = connection.prepareStatement("insert into coupon (code,discount,exp_date) values(?,?,?)")) {
			statement.setString(1, coupon.getCode());
			statement.setBigDecimal(2, coupon.getDiscount());
			statement.setString(3, coupon.getExpDate());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 修正方法名为 findByCode，返回未找到时 id==0 的 Coupon
	public Coupon findByCode(String code) {
		Coupon coupon = new Coupon();
		try (Connection connection = ConnectionUtil.getConnection();
		     PreparedStatement statement = connection.prepareStatement("select id,code,discount,exp_date from coupon where code=?")) {
			statement.setString(1, code);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					coupon.setId(resultSet.getInt("id"));
					coupon.setCode(resultSet.getString("code"));
					coupon.setDiscount(resultSet.getBigDecimal("discount"));
					coupon.setExpDate(resultSet.getString("exp_date"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupon;
	}
}