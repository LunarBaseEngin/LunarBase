package LCG.Examples;

import java.io.IOException;

import LCG.DB.API.LunarDB;
 

public class Case0CreateDB {

	public static void main(String[] args) {
		
		String creation_conf = "/home/feiben/EclipseWorkspace/LunarBaseApplication/creation.conf";
		String root_path = "/home/feiben/DBTest/";
		
		LunarDB l_db = new LunarDB();
		l_db.createDB(root_path, creation_conf);
		
		 
		try {
			l_db.closeDB();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
