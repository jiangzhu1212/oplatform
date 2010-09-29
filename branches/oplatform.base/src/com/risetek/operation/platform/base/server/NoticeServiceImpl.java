package com.risetek.operation.platform.base.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.operation.platform.base.client.service.NoticeService;
import com.risetek.operation.platform.launch.client.entry.Notice;
import com.risetek.operation.platform.launch.server.core.ConnectDataBase;

public class NoticeServiceImpl extends RemoteServiceServlet implements NoticeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Integer getNoticeDataCount() {
		int count = 0;
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select count(*) from RISETEK_NOTICE";
			ResultSet result = statement.executeQuery(SQL);
			if(result.next()){
				count = result.getInt(1);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Notice[] getAllNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notice[] getNoticePage(int rowCount) {
		Notice[] notices = null;
		List<Notice> list = new ArrayList<Notice>();
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select * from RISETEK_NOTICE where rownum<=" + rowCount + " order by ID";
			ResultSet result = statement.executeQuery(SQL);
			while(result.next()){
				Notice notice = new Notice();
				notice.setId(result.getInt(1));
				notice.setContent(result.getString(2));
				notice.setRoles(result.getString(3));
				notice.setAddTime(result.getTimestamp(4));
				notice.setEffectTime(result.getTimestamp(5));
				notice.setFailureTime(result.getTimestamp(6));
				list.add(notice);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			notices = new Notice[list.size()];
			for(int i=0;i<notices.length;i++){
				notices[i] = list.get(i);
			}
		} else {
			notices = new Notice[0];
		}
		return notices;
	}

	@Override
	public Notice[] getNoticePageToPoint(int rowCount, int pagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editNotice(Notice notice) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "update RISETEK_NOTICE set CONTENT = '" + notice.getContent() + "', " +
					"ROLES = '" + notice.getRoles() + "', " +
					"EFFECTTIME = to_date('" + getFormatDateTime(notice.getEffectTime()) + "','YYYY-MM-DD HH24:MI:SS'), " +
					"FAILURETIME = to_date('" + getFormatDateTime(notice.getFailureTime()) + "','YYYY-MM-DD HH24:MI:SS')" +
					" where ID = " + notice.getId();
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addNotice(Notice notice) {
		try {
			Connection conn = ConnectDataBase.CONNDB.getConnection();
			Statement statement = conn.createStatement();
			String SQL = "select G_VALUE from GENERATOR_TABLE where G_KEY='RISETEK_NOTICE'";
			ResultSet result = statement.executeQuery(SQL);
			int id = 0;
			if(result.next()){
				id = result.getInt(1);
			}
			SQL = "insert into RISETEK_NOTICE (ID, CONTENT, ROLES, ADDTIME, EFFECTTIME, " +
					"FAILURETIME) VALUES (" + 
					id + ", '" + notice.getContent() + "', '" +
					notice.getRoles() + "', sysdate" +
					", to_date('" + getFormatDateTime(notice.getEffectTime()) + "','YYYY-MM-DD HH24:MI:SS')" + 
					", to_date('" + getFormatDateTime(notice.getFailureTime()) + "','YYYY-MM-DD HH24:MI:SS'))";
			statement.execute(SQL);
			id++;
			SQL = "update GENERATOR_TABLE set G_VALUE = " + id + " where G_KEY='RISETEK_NOTICE'";
			statement.execute(SQL);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getFormatDateTime(Timestamp timestamp){
		String time = timestamp.toString();
		time = time.substring(0, time.length()-2);
		return time;
	}
}
