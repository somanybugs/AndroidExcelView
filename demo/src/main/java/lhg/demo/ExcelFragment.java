package lhg.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.UUID;

import lhg.excelview.ExcelView;
import lhg.excelview.ExcelView.Span;

public class ExcelFragment extends Fragment {
    private static final String TAG = "ExcelFragment";
    ExcelView excelView;
    MyAdapter adapter;
    View space;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_excel, null);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        excelView = getView().findViewById(R.id.excelView);
        space = getView().findViewById(R.id.space);
        excelView.setAdapter(adapter = new MyAdapter());
        excelView.setBackgroundColor(Color.YELLOW);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        menu.add("滚").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        menu.add("加").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        menu.add("减").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        menu.add("换").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        menu.add("尺").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        menu.add("隔").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        menu.add("色").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("滚")) {
            excelView.scrollTo(800, 800);
        } else if (item.getTitle().equals("加")) {
            adapter.col++;
            adapter.row++;
            adapter.notifyDataSetChanged();
        } else if (item.getTitle().equals("减")) {
            adapter.col--;
            adapter.row--;
            adapter.notifyDataSetChanged();
        } else if (item.getTitle().equals("换")) {
            adapter.testval = UUID.randomUUID().toString().substring(0, 4);
            adapter.notifyDataSetChanged();
        } else if (item.getTitle().equals("尺")) {
            float weight = (float) (3 * Math.random());
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
            params.weight = weight;
            space.setLayoutParams(params);
            adapter.notifyDataSetChanged();
        } else if (item.getTitle().equals("隔")) {
            int width = (int) (20 * Math.random());
            excelView.setDividerWidth(width);
        }else if (item.getTitle().equals("色")) {
            int colors[] = {
                    Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED, Color.LTGRAY,
                    Color.MAGENTA, Color.CYAN
            };
            int i = (int) (colors.length * Math.random());
            excelView.setDividerColor(colors[i]);
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends ExcelView.ExcelAdapter {

        int col = 50, row = 50;

        int iiii = 0;

        int newCount = 0;
        String testval = "测试";

        Span spans[] = new Span[]{
                new Span(2, 2, 4, 3),
                new Span(3, 8, 4, 10),
                new Span(7, 4, 9, 4),
                new Span(6, 8, 8, 8),
        };

        @Override
        public Span querySpan(int row, int col) {
            for (Span span : spans) {
                if (span.contains(row, col)) {
                    return span;
                }
            }
            return null;
        }

        @Override
        public int getColCount() {
            return col;
        }

        @Override
        public int getRowCount() {
            return row;
        }

        @Override
        public int getRowHeight(int row) {
            return 150;
        }

        @Override
        public int getColWidth(int col) {
            return 200;
        }

        @Override
        public int getCellViewType(int row, int col) {
            if (row == 2 && col == 2) {
                return 1;
            }
            return 0;
        }

        @Override
        public View getCellView(Context context, View convertView, int row, int col) {
            if (getCellViewType(row, col) == 1) {
                if (convertView == null) {
                    convertView = new ImageView(context);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "image click", Toast.LENGTH_SHORT).show();
                        }
                    });
                    newCount++;
                    Log.i(TAG, "newCount=" + newCount);
                }
                convertView.setBackgroundColor(Color.WHITE);
                ImageView tv = (ImageView) convertView;
                Glide.with(tv).load("https://image1.8264.com/wen/public/20191208/157576436898.jpg").into(tv);
                return convertView;
            }

            if (convertView == null) {
                convertView = new TextView(context);
                convertView.setBackgroundColor(Color.WHITE);
                newCount++;
                Log.i(TAG, "newCount=" + newCount);
                Toast.makeText(getContext(), "总共" + (getRowCount()*getColCount()) + "单元格, create index = " + (iiii++), Toast.LENGTH_SHORT).show();;
            }
            TextView tv = (TextView) convertView;
            tv.setGravity(Gravity.CENTER);
            tv.setText(String.format("%d, %d", row, col));
            if (row == 3 && col == 3) {
                tv.setText(testval);
            }

            if (row == 0 || col == 0) {
                convertView.setBackgroundColor(0xffffe4b5);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }
            return convertView;
        }
    }
}
