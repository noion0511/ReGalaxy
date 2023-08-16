import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemRankListBinding
import com.ssafy.phonesin.model.Rank

class HomeRankAdapter(private val rankList: MutableLiveData<List<Rank>>) :
    RecyclerView.Adapter<HomeRankAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRankListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, rank: Rank) = with(binding) {
            if (position == 0) {
                imageViewRank.setImageResource(R.drawable.rank1)
            } else if (position == 1) {
                imageViewRank.setImageResource(R.drawable.rank2)
            } else if (position == 2) {
                imageViewRank.setImageResource(R.drawable.rank3)
            }

            textViewName.setText(convertName(rank.memberName))
            textViewPhoneCnt.setText(rank.donationCount.toString())
        }

        fun convertName(name: String) : String {
            val stringBuilder = StringBuilder(name)
            if (stringBuilder.length > 1) {
                stringBuilder.setCharAt(1, '*')
                stringBuilder.insert(1, ' ')
                stringBuilder.insert(3, ' ')
            }

            return stringBuilder.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRankListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        rankList.value?.let { viewHolder.bind(position, it.get(position)) }
    }

    override fun getItemCount() = rankList.value?.size ?: 0

}
