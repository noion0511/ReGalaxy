import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.ItemAddressListBinding

class RegistedAddressAdapter(
    private val addressList: List<String>,
    private val onRemoveClickListener: OnRemoveClickListener
) :
    RecyclerView.Adapter<RegistedAddressAdapter.ViewHolder>() {

    interface OnRemoveClickListener {
        fun onRemoveClick(address: String)
    }

    inner class ViewHolder(private val binding: ItemAddressListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: String) {
            binding.textViewRegistedAddress.setText(address)

            binding.imageViewRemoveAddress.setOnClickListener {
                onRemoveClickListener.onRemoveClick(address)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(addressList[position])
    }

    override fun getItemCount() = addressList.size

}
