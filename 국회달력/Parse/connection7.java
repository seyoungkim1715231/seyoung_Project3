package dbtest;
//�����ͺ��̽����� ���̸� �ҷ����� 
//for�������� �����鼭 url������
import java.sql.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class connection7 {
public void datainfo()throws ClassNotFoundException {//Ư���������� ����
		
		try{	
			
			   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	           String connectionUrl = "jdbc:sqlserver://localhost:1433;database=seyoung;integratedSecurity=true";
			   Connection con = DriverManager.getConnection(connectionUrl);
	           Statement stmt = con.createStatement();
	           System.out.println("MS-SQL ���� ���ӿ� �����Ͽ����ϴ�.");
	           String sql1="SELECT NUM,DRUG_NAME FROM EFCYINFO";
	           String sql = "INSERT INTO AGEWARNING (NUM,MIX_TYPE, INGR_CODE, INGR_ENG_NAME, INGR_NAME, ORI_INGR, CLASS_NAME, FORM_NAME, GRADE, PROHBT_CONTENT) VALUES (?,?,?,?,?,?,?,?,?,?)";
	           PreparedStatement pstmt = null;
	           int num=0;
	           int number=0;//�� number�� efcyinfo���̺��� NUM��.
	           
	           for (int i=1;i<=number;number++) {
	        	   
	        	   
	           }

	          
		    	for (int page=1;page<4;page++) {//4����
		    		 String pageNum=Integer.toString(page);
		    		 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DURIrdntInfoService01/getSpcifyAgrdeTabooInfoList"); /*URL*/
		    	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=LgPg%2BnD2Oy1X1zQBRYI%2FoGjOVdSa17g9KbfkO2xI7ZF68WYcYHtkCncTTmzS5Uw22mSynmpVfnW7TlF0o5WexA%3D%3D"); /*Service Key*/
		    	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNum, "UTF-8")); /*��������ȣ*/
		    	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*�� ������ ��� ��*/
		    	        
		    	        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*���䵥���� ����(xml/json) default : xml*/
		    	        URL url = new URL(urlBuilder.toString());
		    	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    	        conn.setRequestMethod("GET");
		    	        conn.setRequestProperty("Content-type", "application/json");
		    	        System.out.println("Response code: " + conn.getResponseCode());
		    	       
		    	        BufferedReader rd;
		    	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		    	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		    	        } else {
		    	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		    	        }
		    	        String line;
		    	        String result="";		    	    
		    	        while ((line = rd.readLine()) != null) {
		    	        	result = result.concat(line);
		    	        }
		    	        
		    	        JSONParser parser = new JSONParser();	
		    			JSONObject obj=(JSONObject)parser.parse(result);
		    			
		    			
		    			JSONObject parse_body=(JSONObject)obj.get("body");
		    			//System.out.println(parse_body.toString());
		    			JSONArray parse_listArr = (JSONArray) parse_body.get("items");
		    			/* System.out.println(parse_listArr.toString()); *///����Ʈ�� ������ ���� 
		    			//System.out.println(parse_listArr.size());//3���� ���������� ����
		    			
		    			if (parse_listArr.size() > 0) {
		    				//pstmt = con.prepareStatement(sql);
		    				for (int i = 0; i < parse_listArr.size(); i++) {
		    					pstmt = con.prepareStatement(sql);
		    					num+=1;
		    					JSONObject drug = (JSONObject) parse_listArr.get(i);
		    					/* System.out.println(drug.toString()); */
		    					pstmt.setInt(1, num);
		    					String mix_t = (String) drug.get("MIX_TYPE");//����/����
		    					pstmt.setString(2, mix_t);
		    					//System.out.println(mix_t);
		    					String ingr_code = (String) drug.get("INGR_CODE");//�����ڵ�
		    					pstmt.setString(3, ingr_code);
		    					String ingr_Ename = (String) drug.get("INGR_ENG_NAME");//���п���
		    					pstmt.setString(4, ingr_Ename);
		    					String ingr_Kname = (String) drug.get("INGR_NAME");//�����ѱ�
		    					pstmt.setString(5, ingr_Kname);
		    					String ori = (String) drug.get("ORI_INGR");//���輺��
		    					pstmt.setString(6, ori);
		    					String efclass = (String) drug.get("CLASS_NAME");//��ȿ�з��ڵ�
		    					pstmt.setString(7, efclass);
		    					String form = (String) drug.get("FORM_NAME");//����
		    					pstmt.setString(8, form);
		    					String grade = (String) drug.get("GRADE");//���
		    					pstmt.setString(9, grade);
		    					String warncontent = (String) drug.get("PROHBT_CONTENT");//�ݱ⳻��
		    					pstmt.setString(10, warncontent);

		    					
		    					
		    					
		    					//StringBuffer sb1 = new StringBuffer();
		    					
								/*
								 * sb1.append("\n ����/���� : " + mix_t + ", �����ڵ� : " + ingr_code + " ���п���: " +
								 * ingr_Ename + "�����ѱ� : " + ingr_Kname + "���輺��: " + ori + "��ȿ�з��ڵ� : " + efclass
								 * + ", ����: " + form + ", ���: " + grade +", \n�ݱ⳻��: "+warncontent);
								 */
								/*
								 * System.out.println(sb1.toString());
								 * System.out.println("------------------------------------");
								 */
								/*
								 * writer.write(sb1.toString());
								 * writer.write("-----------------------------------------------------\n");
								 */
		    					int resultsql = pstmt.executeUpdate();
				    			System.out.println("ó���� ���ڵ� ��"+resultsql);
				    			
		    				}

		    			}
		    			
		    	        rd.close();
		    	        conn.disconnect();
		    			/* System.out.println(sb.toString()); */
		    		
		    	}
		    	stmt.close();   
		        con.close(); 
		        pstmt.close();
		    }catch(SQLException e) {
		    	System.out.println(e+ "=> Sql ���� ");
		    }catch (Exception e) {
				e.printStackTrace();
		    }

	}
	
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException {
		connection7 ct = new connection7();
		ct.datainfo();
  
		
		
       
    }//rs.close( );stmt.close( );conn.close( ); ����ó���� �� ���� �ݾ��ֱ�

}


