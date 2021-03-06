package net.corda.sdk.token.types.token

import net.corda.core.contracts.LinearPointer
import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal

/**
 * [EmbeddableToken]s are [Token]s which can be composed into an [OwnedToken] or an [OwnedTokenAmount].
 */
@CordaSerializable
sealed class EmbeddableToken : Token

/**
 * A [FixedToken] is where the definition of a token is inlined into the [OwnedToken] or [OwnedTokenAmount] class.
 * This is for tokens which you don't expect to evolve. Note that [FixedToken]s are not a states! They don't need to be
 * because the definition of the token never changes or hardly ever changes. You certainly likely to use this when
 * testing or creating tokens for currencies, in that regard, see [Money].
 *
 * If you do need to make changes to a [FixedToken] then they need to be made through upgrading your CorDapp. Then by
 * redeeming and re-issuing the [OwnedToken] or [OwnedTokenAmount] with the new token. However, if your token is likely
 * to need updating, then use the [EvolvableToken] type.
 */
abstract class FixedToken : EmbeddableToken()

/**
 * To harness the power of [EvolvableToken]s, they cannot be directly embedded in [OwnedToken] or [OwnedTokenAmoun]s.
 * Instead, a [TokenPointer] is embedded. The pointer can be resolved inside the verify function to obtain the data
 * within the token defintion. This way, the [Token] can evolve independently from who owns it as the data is held in
 * separate state objects.
 */
data class TokenPointer<T : EvolvableToken>(
        val pointer: LinearPointer<T>,
        override val displayTokenSize: BigDecimal
) : EmbeddableToken() {
    override fun toString(): String = "Pointer(${pointer.pointer.id}, ${pointer.type.canonicalName})"
}










