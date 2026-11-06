package cat.institutmarianao.a1_pbackendalnvol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador de RecyclerView
class IngredientAdapter(
    private val ingredientList: MutableList<Ingredient>,
    private val onItemClick: (Ingredient) -> Unit,
    private val onDeleteClick: (Ingredient) -> Unit
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvIngredientName)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        val tvExpiration: TextView = itemView.findViewById(R.id.tvExpirationDate)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.tvName.text = ingredient.name
        holder.tvQuantity.text = "${ingredient.quantity} ${ingredient.unit}"
        holder.tvExpiration.text = ingredient.expirationDate

        holder.itemView.setOnClickListener { onItemClick(ingredient) }
        holder.btnDelete.setOnClickListener { onDeleteClick(ingredient) }
    }

    override fun getItemCount(): Int = ingredientList.size
}