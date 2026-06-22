import Foundation

/// A Nucleus token type decodable from its type-scoped wire token string.
///
/// The SDUI backend emits type-scoped tokens:
/// - Font: `s3`
/// - Color: `text.primary`
/// - Button: `inverse.32`
/// - Icon: `name.variant`
///
/// Conformers are `Decodable`. Each Nucleus primitive conforms in its own module (via a generated `+TokenResolvable.swift`), so a consumer only links the modules it actually imports.
///
/// ``resolve(token:)`` returns `nil` for an unknown token and trips `assertionFailure` in debug builds so a stale or mistyped token surfaces during development. The default ``init(from:)`` turns a `nil` resolve into a thrown `DecodingError`, so in release a bad server token fails the decode rather than silently producing a wrong value.
public protocol TokenResolvable: Decodable {
    associatedtype Token: Decodable
    static func resolve(token: Token) -> Self?
}

public extension TokenResolvable {
    init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()
        let token = try container.decode(Token.self)
        guard let resolved = Self.resolve(token: token) else {
            throw DecodingError.dataCorruptedError(
                in: container,
                debugDescription: "unknown \(Self.self) token: \(token)"
            )
        }
        self = resolved
    }
}
