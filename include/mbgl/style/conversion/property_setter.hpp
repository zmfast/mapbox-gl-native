#pragma once

#include <mbgl/style/layer.hpp>
#include <mbgl/style/conversion.hpp>
#include <mbgl/style/conversion/constant.hpp>
#include <mbgl/style/conversion/property_value.hpp>
#include <mbgl/style/conversion/data_driven_property_value.hpp>
#include <mbgl/style/conversion/transition_options.hpp>

#include <string>

namespace mbgl {
namespace style {
namespace conversion {

template <class V>
using PropertySetter = optional<Error> (*) (Layer&, const V&);

template <class V, class L, class PropertyValue, void (L::*setter)(PropertyValue)>
optional<Error> setProperty(Layer& layer, const V& value) {
    auto* typedLayer = layer.as<L>();
    if (!typedLayer) {
        return Error { "layer doesn't support this property" };
    }

    Error error;
    optional<PropertyValue> typedValue = convert<PropertyValue>(value, error);
    if (!typedValue) {
        return error;
    }

    (typedLayer->*setter)(*typedValue);
    return {};
}

template <class V, class L, void (L::*setter)(const TransitionOptions&)>
optional<Error> setTransition(Layer& layer, const V& value) {
    auto* typedLayer = layer.as<L>();
    if (!typedLayer) {
        return Error { "layer doesn't support this property" };
    }

    Error error;
    optional<TransitionOptions> transition = convert<TransitionOptions>(value, error);
    if (!transition) {
        return error;
    }

    (typedLayer->*setter)(*transition);
    return {};
}

template <class V>
optional<Error> setVisibility(Layer& layer, const V& value) {
    if (isUndefined(value)) {
        layer.setVisibility(VisibilityType::Visible);
        return {};
    }

    Error error;
    optional<VisibilityType> visibility = convert<VisibilityType>(value, error);
    if (!visibility) {
        return error;
    }

    layer.setVisibility(*visibility);
    return {};
}

} // namespace conversion
} // namespace style
} // namespace mbgl