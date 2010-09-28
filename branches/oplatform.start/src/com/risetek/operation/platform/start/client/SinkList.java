package com.risetek.operation.platform.start.client;

import java.util.ArrayList;

import com.risetek.operation.platform.base.client.AcountSink;
import com.risetek.operation.platform.base.client.AnnoucementSink;
import com.risetek.operation.platform.base.client.BankSink;
import com.risetek.operation.platform.base.client.BillSink;
import com.risetek.operation.platform.base.client.Card007Sink;
import com.risetek.operation.platform.base.client.CardBatchSink;
import com.risetek.operation.platform.base.client.CardTerminalSink;
import com.risetek.operation.platform.base.client.ChannelSink;
import com.risetek.operation.platform.base.client.CupCertificateSink;
import com.risetek.operation.platform.base.client.CustomerSink;
import com.risetek.operation.platform.base.client.EGoodsSink;
import com.risetek.operation.platform.base.client.JCardQuerySink;
import com.risetek.operation.platform.base.client.MingYAppendMoneySink;
import com.risetek.operation.platform.base.client.PBabySink;
import com.risetek.operation.platform.base.client.RoleConfigSink;
import com.risetek.operation.platform.base.client.TdbcSink;
import com.risetek.operation.platform.base.client.TradeSink;
import com.risetek.operation.platform.base.client.TransBindSink;
import com.risetek.operation.platform.base.client.TransEnableSink;
import com.risetek.operation.platform.base.client.TransactionSink;
import com.risetek.operation.platform.base.client.UserConfigSink;
import com.risetek.operation.platform.launch.client.sink.Sink;
import com.risetek.operation.platform.log.client.LogSink;

/**
 * @author Amber
 * 功能：模块列表类
 * 2010-8-23 下午11:59:28
 */
public class SinkList {
	
	/**
	 * 功能：注册功能模块
	 *
	 * Class[]
	 * @return 返回功能模块列表
	 */
	public static ArrayList<Sink> getSinkList(){
		ArrayList<Sink> sinkList = new ArrayList<Sink>();
//		sinkList.add(new BaseSink());
//		sinkList.add(new ProcessSink());
		sinkList.add(new CustomerSink());
		sinkList.add(new TransactionSink());
		sinkList.add(new BankSink());
		sinkList.add(new AcountSink());
		sinkList.add(new TdbcSink());
		sinkList.add(new AnnoucementSink());
		sinkList.add(new Card007Sink());
		sinkList.add(new TransEnableSink());
		sinkList.add(new RoleConfigSink());
		sinkList.add(new TransBindSink());
		sinkList.add(new CardBatchSink());
		sinkList.add(new CardTerminalSink());
		sinkList.add(new ChannelSink());
		sinkList.add(new JCardQuerySink());
		sinkList.add(new UserConfigSink());
		sinkList.add(new PBabySink());
		sinkList.add(new BillSink());
		sinkList.add(new EGoodsSink());
		sinkList.add(new TradeSink());
		sinkList.add(new CupCertificateSink());
		sinkList.add(new LogSink());
		sinkList.add(new MingYAppendMoneySink());
		return sinkList;
	}
	
}
