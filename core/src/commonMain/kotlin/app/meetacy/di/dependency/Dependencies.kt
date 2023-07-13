package app.meetacy.di.dependency

public data class Dependencies(val list: List<DependencyPair<*>>) {
    init {
        require(list.distinctBy { it.key }.size == list.size) {
            "Dependencies ambiguity found in $list"
        }
    }

    public companion object {
        public val Empty: Dependencies = Dependencies(emptyList())
    }
}
