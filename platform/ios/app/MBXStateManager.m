#import "MBXStateManager.h"
#import <Mapbox/Mapbox.h>
#import "MBXState.h"
#import "MBXViewController.h"

@interface MBXStateManager()

@property (strong, nonatomic) MBXState *currentState;

@end

@implementation MBXStateManager

+ (instancetype) sharedManager {
    static dispatch_once_t once;
    static MBXStateManager* sharedManager;
    dispatch_once(&once, ^{
        sharedManager = [[self alloc] init];
    });

    return sharedManager;
}

- (MBXState*)currentState {
    NSData *encodedMapState = [[NSUserDefaults standardUserDefaults] objectForKey:@"mapStateKey"];
    NSDictionary *decodedMapState = [NSKeyedUnarchiver unarchiveObjectWithData:encodedMapState];

    if (decodedMapState == nil) {
        _currentState = nil;
    } else {
        _currentState = [[MBXState alloc] init];

        if (decodedMapState[MBXCamera] != NULL) {
            MGLMapCamera *unpackedCamera = [NSKeyedUnarchiver unarchiveObjectWithData:decodedMapState[MBXCamera]];
            _currentState.camera = unpackedCamera;
        }

        _currentState.showsUserLocation = [decodedMapState[MBXShowsUserLocation] boolValue];
        _currentState.userTrackingMode = [decodedMapState[MBXUserTrackingMode] boolValue];
        _currentState.showsUserHeadingIndicator = [decodedMapState[MBXMapShowsHeadingIndicator] boolValue];
        _currentState.showsMapScale = [decodedMapState[MBXShowsMapScale] boolValue];
        _currentState.showsZoomLevelOrnament = [decodedMapState[MBXShowsZoomLevelOrnament] boolValue];
        _currentState.showsTimeFrameGraph = [decodedMapState[MBXShowsTimeFrameGraph] boolValue];
        _currentState.framerateMeasurementEnabled = [decodedMapState[MBXMapFramerateMeasurementEnabled] boolValue];
        _currentState.debugMask = ((NSNumber *)decodedMapState[MBXDebugMaskValue]).intValue;
        _currentState.debugLoggingEnabled = [decodedMapState[MBXDebugLoggingEnabled] boolValue];
    }

    return _currentState;
}

- (void)saveState:(MBXState*)mapState {

    NSMutableDictionary *mapStateDictionary = [NSMutableDictionary dictionary];

    NSData *cameraData = [NSKeyedArchiver archivedDataWithRootObject:mapState.camera];
    [mapStateDictionary setObject:cameraData forKey:MBXCamera];
    [mapStateDictionary setValue:@(mapState.showsUserLocation) forKey:MBXShowsUserLocation];
    [mapStateDictionary setValue:@(mapState.userTrackingMode) forKey:MBXUserTrackingMode];
    [mapStateDictionary setValue:@(mapState.showsUserHeadingIndicator) forKey:MBXMapShowsHeadingIndicator];
    [mapStateDictionary setValue:@(mapState.showsMapScale) forKey:MBXShowsMapScale];
    [mapStateDictionary setValue:@(mapState.showsZoomLevelOrnament) forKey:MBXShowsZoomLevelOrnament];
    [mapStateDictionary setValue:@(mapState.showsTimeFrameGraph) forKey:MBXShowsTimeFrameGraph];
    [mapStateDictionary setValue:@(mapState.framerateMeasurementEnabled) forKey:MBXMapFramerateMeasurementEnabled];
    [mapStateDictionary setValue:@(mapState.debugMask) forKey:MBXDebugMaskValue];
    [mapStateDictionary setValue:@(mapState.debugLoggingEnabled) forKey:MBXDebugLoggingEnabled];

    [[NSUserDefaults standardUserDefaults] setObject:mapStateDictionary forKey:@"mapStateKey"];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

- (void)resetState {
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"mapStateKey"];
}



@end
