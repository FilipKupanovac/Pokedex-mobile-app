package filipkupanovac.pokedex_firered.pokedex.helpers

fun parseFavoritesToListInt(favoritesString: String): List<Int> {
    val array = favoritesString.split(',')
    val favoritesList: MutableList<Int> = mutableListOf()
    array.forEach { favorite ->
        if (favorite != "") {
            favoritesList.add(favorite.toInt())
        }
    }
    return favoritesList
}

fun parseFavoritesListToString(favoritesList: List<Int>): String {
    if(favoritesList.isEmpty())
        return ""
    val stringBuilder: StringBuilder = java.lang.StringBuilder()
    favoritesList.forEach { favorite ->
        stringBuilder.append(favorite.toString()).append(",")
    }

    stringBuilder.removeRange(stringBuilder.count() - 1, stringBuilder.count())

    return stringBuilder.toString()
}