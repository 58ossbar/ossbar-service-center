package weixin.popular.bean.wxa;

public class WxaDUserInfo extends WxaUserInfo{

	private String openId;

	private String unionId;

	public String getPurePhoneNumber() {
		return purePhoneNumber;
	}

	public void setPurePhoneNumber(String purePhoneNumber) {
		this.purePhoneNumber = purePhoneNumber;
	}

	private String purePhoneNumber;

	private Watermark watermark;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Watermark getWatermark() {
		return watermark;
	}

	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}

}
