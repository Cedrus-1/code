package com.cedrus.design.prototype.deep;

import java.io.*;
import java.util.Date;

/**
 * @author Cedrus
 * @date 2019/3/3
 */
public class QiTianDaSheng extends Monkey implements Cloneable,Serializable{

	public JinGuBang jinGuBang;

	public QiTianDaSheng(){
		//只是初始化
		this.birthday = new Date();
		this.jinGuBang = new JinGuBang();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return this.deepClone();
	}

	public Object deepClone(){
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			QiTianDaSheng copy = (QiTianDaSheng)ois.readObject();
			copy.birthday = new Date();
			return copy;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
