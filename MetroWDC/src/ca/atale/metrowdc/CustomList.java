package ca.atale.metrowdc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{
	
	private final Activity context;
	private final String[] menuitem;
	private final Integer[] imageId;
	
	public CustomList(Activity context,
			String[] menuitem, Integer[] imageId) {

				super(context, R.layout.main_list, menuitem);
				this.context = context;
				this.menuitem = menuitem;
				this.imageId = imageId;
			}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();

		View rowView= inflater.inflate(R.layout.main_list, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.label_menuitem);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon_menuitem);
		txtTitle.setText(menuitem[position]);
		imageView.setImageResource(imageId[position]);
		return rowView;
	}
}