import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemRankListBinding

class HomeRankAdapter(private val rankList: List<Int>) :
    RecyclerView.Adapter<HomeRankAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRankListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rank: Int) {
            if (rank == 1) {
                binding.imageViewRank.setImageResource(R.drawable.rank1)
            } else if (rank == 2) {
                binding.imageViewRank.setImageResource(R.drawable.rank2)
            } else if (rank == 3) {
                binding.imageViewRank.setImageResource(R.drawable.rank3)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRankListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.textView.text = rankList[position]
        viewHolder.bind(rankList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = rankList.size

}
