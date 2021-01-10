import android.os.Parcelable
import com.airbnb.mvrx.MavericksState
import com.demo.fooddelivery.model.Food
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Foods(
	@SerializedName("type") val type: String,
	@SerializedName("food") val item: List<Food>
) : MavericksState, Parcelable