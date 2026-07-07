import Foundation

extension NucleusFont {
    public struct LineHeight: Equatable, Hashable, Sendable, ExpressibleByFloatLiteral, ExpressibleByIntegerLiteral {
        public let value: Double

        public init(floatLiteral value: Double) {
            self.value = value
        }

        public init(integerLiteral value: IntegerLiteralType) {
            self.value = Double(value)
        }

        public static let tight: LineHeight = 1.0
        public static let regular: LineHeight = 1.2
        public static let loose: LineHeight = 1.3
    }
}
