package app.meetacy.di.internal

import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection

@PublishedApi
internal fun KType.parametrize(
    vararg parameters: KTypeProjection
): KType = object : KType {
    override val arguments = parameters.toList()
    override val classifier: KClassifier? = this@parametrize.classifier
    override val isMarkedNullable: Boolean = this@parametrize.isMarkedNullable
}

@PublishedApi
internal fun KType.parametrize(
    vararg parameters: KType
): KType = parametrize(
    *parameters.map { type ->
        KTypeProjection(
            variance = null,
            type = type
        )
    }.toTypedArray()
)
