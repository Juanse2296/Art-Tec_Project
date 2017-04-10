package back;

import org.jbox2d.common.Vec2;

public class FormBack {

	protected Vec2 pos;
	protected int size;
	protected Vec2 form[];

	public FormBack(String data) {
		getData(data);
	}

	public void getData(String data) {
		String[] d = data.split(",");
		pos = new Vec2(Integer.valueOf(d[0]), Integer.valueOf(d[1]));
		size = Integer.valueOf(d[2]);
		String[] points = d[3].split("/");
		form = new Vec2[points.length];

		for (int i = 0; i < points.length; i++) {
			String[] a = points[i].split(";");
			form[i] = new Vec2(Integer.valueOf(a[0]), Integer.valueOf(a[1]));
		}
		
		System.out.println("pos: " + pos);
		System.out.println("size: " + size);
		System.out.println("points form: " + form.length);
		System.out.println("--------------------------------------");
	}

}
