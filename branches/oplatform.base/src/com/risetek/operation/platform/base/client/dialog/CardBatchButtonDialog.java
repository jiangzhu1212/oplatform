package com.risetek.operation.platform.base.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.operation.platform.base.client.control.CardBatchController;
import com.risetek.operation.platform.base.client.model.CardBatchData;
import com.risetek.operation.platform.base.client.model.EPay2Packet;
import com.risetek.operation.platform.launch.client.http.RequestFactory;
import com.risetek.operation.platform.launch.client.json.constanst.CardBatchConstanst;
import com.risetek.operation.platform.launch.client.json.constanst.Constanst;
import com.risetek.operation.platform.launch.client.util.Util;

public class CardBatchButtonDialog extends BaseButtonDailog {
	
	private final Label BATCH_ID_ZH = new Label(CardBatchConstanst.BATCH_ID_ZH);
	private final Label CHANNEL_ID_ZH = new Label(CardBatchConstanst.CHANNEL_ID_ZH);
	private final Label DESCRIPTION_ZH = new Label(CardBatchConstanst.DESCRIPTION_ZH);
	private final Label PRICE_ZH = new Label(CardBatchConstanst.PRICE_ZH);
	private final Label INDATE_ZH = new Label(CardBatchConstanst.INDATE_ZH);
	private final Label ADDITION_ZH = new Label(CardBatchConstanst.ADDITION_ZH);
	private final Label PRO_BATCH_ZH = new Label(CardBatchConstanst.PRO_BATCH_ZH);
			
	private final TextBox BATCH_ID = new TextBox();
	private final TextBox CHANNEL_ID = new TextBox();
	private final TextBox DESCRIPTION = new TextBox();
	private final TextBox PRICE = new TextBox();
	private final TextBox INDATE = new TextBox();
	private final TextBox ADDITION = new TextBox();
	private final TextBox PRO_BATCH = new TextBox();
	
	public void addMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_ADD_CARD_BATCH ;
		setText("添加批次");
		gridFrame.resize(6, 2);
		gridFrame.setWidget(0, 0, CHANNEL_ID_ZH);
		gridFrame.setWidget(0, 1, CHANNEL_ID);
		gridFrame.setWidget(1, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(1, 1, DESCRIPTION);
		gridFrame.setWidget(2, 0, PRICE_ZH);
		gridFrame.setWidget(2, 1, PRICE);
		gridFrame.setWidget(3, 0, INDATE_ZH);
		gridFrame.setWidget(3, 1, INDATE);
		gridFrame.setWidget(4, 0, ADDITION_ZH);
		gridFrame.setWidget(4, 1, ADDITION);
		gridFrame.setWidget(5, 0, PRO_BATCH_ZH);
		gridFrame.setWidget(5, 1, PRO_BATCH);	
		mainPanel.add(gridFrame);
		submit.setText("添加");
		show();
	}
	
	public void queryMainPanel(){
		mainPanel.clear();
		ACTION_NAME = Constanst.ACTION_NAME_QUERY_CARD_BATCH ;
		setText("查询批次");
		gridFrame.resize(7, 2);
		gridFrame.setWidget(0, 0, BATCH_ID_ZH);
		gridFrame.setWidget(0, 1, BATCH_ID);
		gridFrame.setWidget(1, 0, CHANNEL_ID_ZH);
		gridFrame.setWidget(1, 1, CHANNEL_ID);
		gridFrame.setWidget(2, 0, DESCRIPTION_ZH);
		gridFrame.setWidget(2, 1, DESCRIPTION);
		gridFrame.setWidget(3, 0, PRICE_ZH);
		gridFrame.setWidget(3, 1, PRICE);
		gridFrame.setWidget(4, 0, INDATE_ZH);
		gridFrame.setWidget(4, 1, INDATE);
		gridFrame.setWidget(5, 0, ADDITION_ZH);
		gridFrame.setWidget(5, 1, ADDITION);
		gridFrame.setWidget(6, 0, PRO_BATCH_ZH);
		gridFrame.setWidget(6, 1, PRO_BATCH);			
		mainPanel.add(gridFrame);
		submit.setText("查询");
		show();
	}

	@Override
	public void subminHandler() {
		// TODO Auto-generated method stub
		String id = BATCH_ID.getText();
		String channel_id = CHANNEL_ID.getText();
		String description = DESCRIPTION.getText();
		String price = PRICE.getText();
		String indate = INDATE.getText();
		String addition = ADDITION.getText();
		String pro_batch = PRO_BATCH.getText();
		
		CardBatchData cardBatchData = new CardBatchData();
		cardBatchData.setACTION_NAME(ACTION_NAME);
		if(id != null && !"".equals(id.trim())){
			if(!Util.isNum(id)){
				setMessage("批次索引必须为数字");
				return ;
			}
			cardBatchData.setBatch_id(Integer.parseInt(id));
		}
		cardBatchData.setChannel_id(channel_id);
		cardBatchData.setDescription(description);
		cardBatchData.setPrice(price);
		cardBatchData.setIndate(indate);
		cardBatchData.setAddition(addition);
		cardBatchData.setPro_batch(pro_batch);
		
		if(Constanst.ACTION_NAME_ADD_CARD_BATCH.equals(ACTION_NAME)){
			if(pro_batch == null || "".equals(pro_batch.trim())){
				setMessage("批次号不能为空");
				return ;
			}
			String jsonStr = cardBatchData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;
			request.getBill(packet, CardBatchController.RemoteCaller);
			hide();
		}else if(Constanst.ACTION_NAME_QUERY_CARD_BATCH.equals(ACTION_NAME)){
			CardBatchController.queryData = cardBatchData;
			String jsonStr = cardBatchData.toHttpPacket();
			EPay2Packet epay2Packet = new EPay2Packet(jsonStr);
			String json = EPay2Packet.listToString(epay2Packet);
			String packet = RequestFactory.PACKET + "="+ json ;
			request.getBill(packet, CardBatchController.QueryCaller);
			hide();
		}
	}
	
}
