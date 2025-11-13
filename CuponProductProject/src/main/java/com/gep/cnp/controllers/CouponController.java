package com.gep.cnp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gep.cnp.model.Coupon;
import com.gep.cnp.dao.CouponDAO;

/**
 * Servlet implementation class CouponController
 */
@WebServlet("/coupons")
public class CouponController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CouponDAO dao = new CouponDAO();

	public CouponController() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("create".equals(action)) {
			createCoupon(request, response);
		} else if ("find".equals(action)) {
			findCoupon(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
		}
	}

	private void findCoupon(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String couponCode = request.getParameter("couponCode");
		Coupon coupon = dao.findByCode(couponCode);

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (coupon != null && coupon.getId() != 0) {
			out.print("<pre>" + coupon.toString() + "</pre>");
		} else {
			out.print("<b>Coupon not found.</b>");
		}
		out.print("<br/> <a href='index.html'>Home</a>");
	}

	private void createCoupon(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String couponCode = request.getParameter("couponCode");
		String discount = request.getParameter("discount");
		String expiryDate = request.getParameter("expiryDate");

		Coupon coupon = new Coupon();
		coupon.setCode(couponCode);
		try {
			coupon.setDiscount(new BigDecimal(discount));
		} catch (Exception e) {
			coupon.setDiscount(BigDecimal.ZERO);
		}
		coupon.setExpDate(expiryDate);

		dao.save(coupon);

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<b>Coupon created!!</b>");
		out.print("<br/> <a href='index.html'>Home</a>");
	}
}