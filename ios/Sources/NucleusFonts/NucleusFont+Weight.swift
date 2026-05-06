import Foundation

extension NucleusFont {
    public struct Weight: Equatable, Hashable, Sendable, ExpressibleByIntegerLiteral {
        public let value: Int

        public init(integerLiteral value: IntegerLiteralType) {
            self.value = value
        }

        public static let regular: Weight = 350
        public static let medium: Weight = 500
        public static let semibold: Weight = 600
        public static let bold: Weight = 650
        public static let extrabold: Weight = 700
    }
}
