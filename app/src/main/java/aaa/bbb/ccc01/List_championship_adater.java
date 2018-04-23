package aaa.bbb.ccc01;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class List_championship_adater extends RecyclerView.Adapter<List_championship_adater.ItemViewHolder>{
    private final Context context;

    private List<Championship> listData;

    public List_championship_adater(Context context) {
        this.context = context;
    }

    public  void setData(List<Championship> listDat)
    {
        this.listData = listDat;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_championship_adater, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.championshipPosition.setText(listData.get(position).championshipPosition);
        holder.championshipTeam1.setText(listData.get(position).championshipTeam1);
        holder.championshipTeam2.setText(listData.get(position).championshipTeam2);
        holder.date_time_text.setText(listData.get(position).date_time);
        holder.result_text.setText(listData.get(position).result_text);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if(listData == null)
            return 0;
        return listData.size();
    }

    public class ItemViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView championshipTeam1;
        public TextView championshipTeam2;
        public TextView result_text;
        public TextView date_time_text;
        public TextView championshipPosition;

        public ItemViewHolder(View itemView) {
            super(itemView);
            championshipTeam1 = itemView.findViewById(R.id.championshipTeam1);
            championshipTeam2 = itemView.findViewById(R.id.championshipTeam2);
            result_text = itemView.findViewById(R.id.result_text);
            date_time_text = itemView.findViewById(R.id.date_time_text);
            championshipPosition = itemView.findViewById(R.id.championshipPosition);

        }

        @Override
        public void onClick(View view) {

        }
    }


}
