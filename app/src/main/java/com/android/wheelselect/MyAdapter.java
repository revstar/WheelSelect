package com.android.wheelselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wu on 2015/12/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.AgeItemViewHolder> {


    private Context mContext;
    /**
     * 起始年龄
     */
    private int startNum;
    /**
     * 终止年龄
     */
    private int endNum;
    /**
     * 显示的数据个数
     */
    public int ITEM_NUM = 7;
    /**
     * 当前选中位置，高亮显示
     */
    private int mHighlight = -1;

    /**
     * 点击事件
     */
    private MyAdapter.OnItemOnClickListener mOnItemOnClickListener;

    public MyAdapter(Context context, int startNum, int endNum) {
        mContext = context;
        this.startNum = startNum;
        this.endNum = endNum;
    }

    public void setOnItemClickListener(MyAdapter.OnItemOnClickListener onItemClickListener) {
        this.mOnItemOnClickListener=onItemClickListener;
    }

    @Override
    public AgeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_age_item, parent, false);
        //设置宽度
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) getItemWidth();
        return new AgeItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final AgeItemViewHolder holder, final int position) {
        holder.mTextView.setText(String.valueOf(startNum + position));
        if (isSelect(position)) {
            holder.mTextView.setTextSize(30);
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.selected));
        } else {
            holder.mTextView.setTextSize(20);
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.unselected));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemOnClickListener!=null){
                    mOnItemOnClickListener.ItemClick(holder.mTextView,position);
                }
            }
        });

    }

    /**
     * 高亮中心, 更新前后位置
     *
     * @param position 在list中的位置
     */
    public void highlightItem(int position) {
        mHighlight = position;
        int offset = ITEM_NUM / 2;
        for (int i = position - offset; i <= position + offset; ++i)
            notifyItemChanged(i);
    }

    /**
     * 判断某个条目是否是选中状态（居中状态）
     *
     * @param position
     * @return
     */
    private boolean isSelect(int position) {
        return mHighlight == position;
    }

    @Override
    public int getItemCount() {
        return endNum - startNum + 1;
    }


    /**
     * 移动到中间
     *
     * @param item
     */
    public void moveMiddle(View item) {
        float itemWidth =item.getWidth();
        float screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        float scrollWidth = item.getLeft() - (screenWidth / 2 - itemWidth / 2);
        item.scrollBy((int) scrollWidth, 0);
//        item.scrollTo((int)screenWidth/2,0);

    }

    /**
     * 获取每一个条目的宽度
     *
     * @return
     */
    public float getItemWidth() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / ITEM_NUM;
    }



    /**
     * ViewHolder类
     */
    public class AgeItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public AgeItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_age);
            mTextView.setTag(this);

        }


        public TextView getmTextView() {
            return mTextView;
        }
    }

    public interface OnItemOnClickListener {

        void ItemClick(View view, int position);
    }
}
