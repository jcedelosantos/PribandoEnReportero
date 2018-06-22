package com.example.icg_dominicana.pribandoenreportero.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icg_dominicana.pribandoenreportero.Fragments.MyReport;
import com.example.icg_dominicana.pribandoenreportero.Objects.Report;
import com.example.icg_dominicana.pribandoenreportero.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Report> reportsArrayList;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;

//    public MyAdapter(List<Report> reports, Context context) {
//        this.reports = reports;
//        this.context = context;
//    }

        public MyAdapter(ArrayList<Report> reports,Context context) {
        this.reportsArrayList = reports;
        this.context = context;

    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( context).inflate( R.layout.recycler_view_post, parent, false );
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return  position%2==0?R.layout.layout_par:R.layout.layout_impar ;
//    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
//        holder.bind(reports.get(position), (OnItemClickListener) itemClickListener );
        Report reportActual = reportsArrayList.get( position );
        ((ViewHolder)holder).textViewdescription.setText( reportActual.getDescription());
        ((ViewHolder)holder).textViewlatitud.setText(""+ reportActual.getLatitud());
        ((ViewHolder)holder).textViewlongitud.setText(""+ reportActual.getLongitud());

        Picasso.with(context).load(reportActual.url_foto).fit().into( ((ViewHolder)holder).imageView );

    }

    @Override
    public int getItemCount() {return reportsArrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewdescription;
        public TextView textViewlatitud;
        public TextView textViewlongitud;
        public ImageView imageView;

        public  ViewHolder(View itemView){
            super(itemView);
            textViewdescription = (TextView) itemView.findViewById( R.id.id_textView_report );
            textViewlatitud = (TextView) itemView.findViewById( R.id.id_textView_latitud );
            textViewlongitud = (TextView) itemView.findViewById( R.id.id_textView_longitud);
            imageView =  (ImageView) itemView.findViewById(R.id.id_imageView);

        }

        public void bind(final MyReport report, final OnItemClickListener listener) {
//          // this.textViewVehiculo.setText( name );
//            textViewdescription.setText( report.getDescription());
//            textViewlatitud.setText( (int) report.getLatitud() );
//            textViewlongitud.setText( (int) report.getLongitud());
//
//            Picasso.with(context).load( report.getPosition()).fit().into( imageView );
           // imageView.setImageResource( vehiculo.getPosition() );

//            itemView.setOnClickListener(( view) ->{
//                listener.onItemClick(report, getAdapterPosition());
//           });

        }
    }
   public interface OnItemClickListener {
       void onItemClick(Report report, int position);
   }
}

