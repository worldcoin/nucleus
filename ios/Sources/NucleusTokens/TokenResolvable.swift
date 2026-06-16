/// A Nucleus token type that can be resolved from its type-scoped wire token string.
///
/// The SDUI backend emits type-scoped tokens (the namespace the resolver type already implies is
/// stripped): a typography token is `s3`, a color is `text.primary`, a button is `inverse.32`, and
/// an icon is `name.variant`. Each Nucleus primitive conforms in its own module (via a generated
/// `+TokenResolvable.swift`), so a consumer only links the modules it actually imports.
///
/// `resolve(token:)` returns `nil` for an unknown token; conformances also trip `assertionFailure`
/// in debug builds so a stale or mistyped token surfaces during development rather than silently
/// falling through.
public protocol TokenResolvable {
    associatedtype ResolvedType
    static func resolve(token: String) -> ResolvedType?
}
