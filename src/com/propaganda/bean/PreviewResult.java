package com.propaganda.bean;

import java.util.ArrayList;

public class PreviewResult {
	private ArrayList<initialPreviewConfig> initialPreviewConfig;
	private ArrayList<String> initialPreview;
	public ArrayList<initialPreviewConfig> getInitialPreviewConfig() {
		return initialPreviewConfig;
	}
	public void setInitialPreviewConfig(
			ArrayList<initialPreviewConfig> initialPreviewConfig) {
		this.initialPreviewConfig = initialPreviewConfig;
	}
	public ArrayList<String> getInitialPreview() {
		return initialPreview;
	}
	public void setInitialPreview(ArrayList<String> initialPreview) {
		this.initialPreview = initialPreview;
	}
}
