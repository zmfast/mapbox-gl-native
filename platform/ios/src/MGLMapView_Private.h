#import "MGLMapView.h"
#import "MGLUserLocationAnnotationView.h"
#import "MGLAnnotationContainerView.h"

#include <mbgl/util/size.hpp>

namespace mbgl {
    class Map;
    class Renderer;
}

@class MGLSource;

/// Minimum size of an annotation’s accessibility element.
FOUNDATION_EXTERN const CGSize MGLAnnotationAccessibilityElementMinimumSize;

/// Indicates that a method (that uses `mbgl::Map`) was called after app termination.
FOUNDATION_EXTERN MGL_EXPORT MGLExceptionName const MGLUnderlyingMapUnavailableException;

@interface MGLMapView (Private)

/// The map view’s OpenGL rendering context.
@property (nonatomic, readonly) EAGLContext *context;

/// Currently shown popover representing the selected annotation.
@property (nonatomic) UIView<MGLCalloutView> *calloutViewForSelectedAnnotation;

/// Map observers
- (void)cameraWillChangeAnimated:(BOOL)animated;
- (void)cameraIsChanging;
- (void)cameraDidChangeAnimated:(BOOL)animated;
- (void)mapViewWillStartLoadingMap;
- (void)mapViewDidFinishLoadingMap;
- (void)mapViewDidFailLoadingMapWithError:(NSError *)error;
- (void)mapViewWillStartRenderingFrame;
- (void)mapViewDidFinishRenderingFrameFullyRendered:(BOOL)fullyRendered;
- (void)mapViewWillStartRenderingMap;
- (void)mapViewDidFinishRenderingMapFullyRendered:(BOOL)fullyRendered;
- (void)mapViewDidBecomeIdle;
- (void)mapViewDidFinishLoadingStyle;
- (void)sourceDidChange:(MGLSource *)source;

/** Triggers another render pass even when it is not necessary. */
- (void)setNeedsRerender;

/// Synchronously render a frame of the map.
- (void)renderSync;

- (mbgl::Map *)mbglMap;

- (mbgl::Renderer *)renderer;

/** Returns whether the map view is currently loading or processing any assets required to render the map */
- (BOOL)isFullyLoaded;

/** Empties the in-memory tile cache. */
- (void)didReceiveMemoryWarning;

- (void)pauseRendering:(NSNotification *)notification;
- (void)resumeRendering:(NSNotification *)notification;
@property (nonatomic) MGLUserLocationAnnotationView *userLocationAnnotationView;
@property (nonatomic) MGLAnnotationContainerView *annotationContainerView;
@property (nonatomic, readonly) BOOL enablePresentsWithTransaction;

- (BOOL) _opaque;

@end
