import com.google.gson.annotations.SerializedName

data class FoodResponse (
	@SerializedName("status") val status : String,
	@SerializedName("foods") val foods : List<Foods>
)