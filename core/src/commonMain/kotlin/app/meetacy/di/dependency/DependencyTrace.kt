package app.meetacy.di.dependency

public data class DependencyTrace(
    val list: List<DependencyKey<*>>
) {
    public companion object {
        public val Empty: DependencyTrace = DependencyTrace(emptyList())
    }
}
