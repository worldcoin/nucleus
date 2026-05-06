import Foundation

extension NucleusFont {
    public struct LineHeight: Equatable, Hashable, Sendable, ExpressibleByFloatLiteral {
        public let value: Double

        public init(floatLiteral value: Double) {
            self.value = value
        }

        public static let tight: LineHeight = 1.0
        public static let regular: LineHeight = 1.2
        public static let loose: LineHeight = 1.3
    }
}
