package com.duoduo.duoduocampus.web;

/**
 * Web页面元素控制器 通过{@link #getInstance}实例化
 */
public class DuoduoWebViewController {
	private boolean isShowCart = true;// 是否展示购物车按钮
	private boolean isShowBack = true;// 是否展示返回按键
	private boolean isShowClose = false;// 是否展示关闭按键
	private boolean isCanBack = true;// 是否可以逐级返回

	public static DuoduoWebViewController getInstance() {
		return new DuoduoWebViewController();
	}

	public boolean isShowCart() {
		return isShowCart;
	}

	/**
	 * 设置购物车按钮的显隐
	 * 
	 * @param isShowCart
	 * @return
	 */
	public DuoduoWebViewController setShowCart(boolean isShowCart) {
		this.isShowCart = isShowCart;
		return this;
	}

	public boolean isShowBack() {
		return isShowBack;
	}

	/**
	 * 设置返回按钮是否显示
	 * 
	 * @param isShowBack
	 * @return
	 */
	public DuoduoWebViewController setShowBack(boolean isShowBack) {
		this.isShowBack = isShowBack;
		return this;
	}

	public boolean isShowClose() {
		return isShowClose;
	}

	/**
	 * 设置关闭按钮是否显示
	 * 
	 * @param isShowClose
	 * @return
	 */
	public DuoduoWebViewController setShowClose(boolean isShowClose) {
		this.isShowClose = isShowClose;
		return this;
	}

	public boolean isCanBack() {
		return isCanBack;
	}

	/**
	 * 设置是否可以返回，比如说退款类型web页面不能直接返回
	 * 
	 * @param isCanBack
	 * @return
	 */
	public DuoduoWebViewController setCanBack(boolean isCanBack) {
		this.isCanBack = isCanBack;
		return this;
	}
}