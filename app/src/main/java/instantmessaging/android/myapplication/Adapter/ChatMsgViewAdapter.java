package instantmessaging.android.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import instantmessaging.android.myapplication.model.ChatMsgEntity;
import instantmessaging.android.myapplication.R;

/**
 * Created by dell on 2017/4/18.
 */

public class ChatMsgViewAdapter extends BaseAdapter{



    final int IMVT_COM_MSG=0;
    final int IMVT_TO_MSG=1;
    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private List<ChatMsgEntity> data;
    private Context context;
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsgEntity entity = data.get(position);
        if (entity.getMsgType())
        {
            return IMVT_COM_MSG;
        }else{
            return IMVT_TO_MSG;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */


    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgEntity entity = data.get(position);
        boolean isComMsg = entity.getMsgType();
        ViewHolder viewHolder = null;


        if (convertView == null) {
            if (isComMsg) {
                convertView = mInflater.inflate(R.layout.text_left, null);
            } else {
                convertView = mInflater.inflate(R.layout.text_right, null);
            }
            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.isComMsg = isComMsg;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getName());
        viewHolder.tvContent.setText(entity.getText());
        return convertView;
    }
    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }



}
