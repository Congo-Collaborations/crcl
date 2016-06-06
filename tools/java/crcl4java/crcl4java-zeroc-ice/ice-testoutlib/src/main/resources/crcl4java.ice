module java2slice {
	module testinlib {
		class TestClassIce;
	};
	module crcl {
		module base {
			class DataThingTypeIce;
		};
	};
	module crcl {
		module base {
			class CRCLProgramTypeIce;
		};
	};
	module crcl {
		module base {
			class RotSpeedTypeIce;
		};
	};
	module crcl {
		module base {
			class RotAccelTypeIce;
		};
	};
	module crcl {
		module base {
			class CRCLCommandTypeIce;
		};
	};
	module crcl {
		module base {
			class MiddleCommandTypeIce;
			sequence<MiddleCommandTypeIce> MiddleCommandTypeIceSequence;
		};
	};
	module crcl {
		module base {
			class ActuateJointsTypeIce;
		};
	};
	module crcl {
		module base {
			class PoseTypeIce;
			sequence<PoseTypeIce> PoseTypeIceSequence;
		};
	};
	module crcl {
		module base {
			class JointDetailsTypeIce;
		};
	};
	module crcl {
		module base {
			class JointSpeedAccelTypeIce;
		};
	};
	module crcl {
		module base {
			class TransSpeedTypeIce;
		};
	};
	module crcl {
		module base {
			class TransSpeedAbsoluteTypeIce;
		};
	};
	module crcl {
		module base {
			class PoseToleranceTypeIce;
		};
	};
	module crcl {
		module base {
			class TransAccelTypeIce;
		};
	};
	module crcl {
		module base {
			class WrenchTypeIce;
		};
	};
	module crcl {
		module base {
			class GripperStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class InitCanonTypeIce;
		};
	};
	module crcl {
		module base {
			class MessageTypeIce;
		};
	};
	module crcl {
		module base {
			enum AngleUnitEnumTypeIce {
				DEGREE=0,
				RADIAN=1
			};
		};
	};
	module crcl {
		module base {
			class SetIntermediatePoseToleranceTypeIce;
		};
	};
	module crcl {
		module base {
			class TransAccelAbsoluteTypeIce;
		};
	};
	module crcl {
		module base {
			class StopMotionTypeIce;
		};
	};
	module crcl {
		module base {
			class VectorTypeIce;
		};
	};
	module crcl {
		module base {
			class SetEndEffectorTypeIce;
		};
	};
	module crcl {
		module base {
			class CloseToolChangerTypeIce;
		};
	};
	module crcl {
		module base {
			enum ForceUnitEnumTypeIce {
				NEWTON=0,
				POUND=1,
				OUNCE=2
			};
		};
	};
	module crcl {
		module base {
			enum TorqueUnitEnumTypeIce {
				NEWTON_METER=0,
				FOOT_POUND=1
			};
		};
	};
	module crcl {
		module base {
			class ConfigureJointReportsTypeIce;
		};
	};
	module crcl {
		module base {
			class CRCLCommandInstanceTypeIce;
		};
	};
	module crcl {
		module base {
			class SetRotSpeedTypeIce;
		};
	};
	module crcl {
		module base {
			class ConfigureStatusReportTypeIce;
		};
	};
	module crcl {
		module base {
			class SetTorqueUnitsTypeIce;
		};
	};
	module crcl {
		module base {
			class JointLimitTypeIce;
			sequence<JointLimitTypeIce> JointLimitTypeIceSequence;
		};
	};
	module crcl {
		module base {
			class ConfigureJointReportTypeIce;
			sequence<ConfigureJointReportTypeIce> ConfigureJointReportTypeIceSequence;
		};
	};
	module crcl {
		module base {
			class SetEndPoseToleranceTypeIce;
		};
	};
	module crcl {
		module base {
			class ParameterSettingTypeIce;
			sequence<ParameterSettingTypeIce> ParameterSettingTypeIceSequence;
		};
	};
	module crcl {
		module base {
			class SetTransSpeedTypeIce;
		};
	};
	module crcl {
		module base {
			class JointStatusesTypeIce;
		};
	};
	module crcl {
		module base {
			class ParallelGripperStatusTypeIce;
		};
	};
	module crcl {
		module base {
			enum LengthUnitEnumTypeIce {
				METER=0,
				MILLIMETER=1,
				INCH=2
			};
		};
	};
	module crcl {
		module base {
			class SetRotAccelTypeIce;
		};
	};
	module crcl {
		module base {
			class SetAngleUnitsTypeIce;
		};
	};
	module crcl {
		module base {
			class CommandStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class PoseStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class TransSpeedRelativeTypeIce;
		};
	};
	module crcl {
		module base {
			class SetForceUnitsTypeIce;
		};
	};
	module crcl {
		module base {
			class JointStatusTypeIce;
			sequence<JointStatusTypeIce> JointStatusTypeIceSequence;
		};
	};
	module crcl {
		module base {
			class VacuumGripperStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class SetEndEffectorParametersTypeIce;
		};
	};
	module crcl {
		module base {
			class EndCanonTypeIce;
		};
	};
	module crcl {
		module base {
			class CRCLStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class RotSpeedRelativeTypeIce;
		};
	};
	module crcl {
		module base {
			class RunProgramTypeIce;
		};
	};
	module crcl {
		module base {
			class RotAccelRelativeTypeIce;
		};
	};
	module crcl {
		module base {
			class MoveThroughToTypeIce;
		};
	};
	module crcl {
		module base {
			class GetStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class TwistTypeIce;
		};
	};
	module crcl {
		module base {
			class MoveScrewTypeIce;
		};
	};
	module crcl {
		module base {
			class RotAccelAbsoluteTypeIce;
		};
	};
	module crcl {
		module base {
			class SetMotionCoordinationTypeIce;
		};
	};
	module crcl {
		module base {
			class ActuateJointTypeIce;
			sequence<ActuateJointTypeIce> ActuateJointTypeIceSequence;
		};
	};
	module crcl {
		module base {
			enum StopConditionEnumTypeIce {
				IMMEDIATE=0,
				FAST=1,
				NORMAL=2
			};
		};
	};
	module crcl {
		module base {
			class RotSpeedAbsoluteTypeIce;
		};
	};
	module crcl {
		module base {
			class ThreeFingerGripperStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class SetRobotParametersTypeIce;
		};
	};
	module crcl {
		module base {
			class MoveToTypeIce;
		};
	};
	module crcl {
		module base {
			class ObjectFactoryIce;
		};
	};
	module crcl {
		module base {
			class PoseAndSetTypeIce;
		};
	};
	module crcl {
		module base {
			class SettingsStatusTypeIce;
		};
	};
	module crcl {
		module base {
			class TransAccelRelativeTypeIce;
		};
	};
	module crcl {
		module base {
			class DwellTypeIce;
		};
	};
	module crcl {
		module base {
			class SetLengthUnitsTypeIce;
		};
	};
	module crcl {
		module base {
			class OpenToolChangerTypeIce;
		};
	};
	module crcl {
		module base {
			class SetTransAccelTypeIce;
		};
	};
	module crcl {
		module base {
			class JointForceTorqueTypeIce;
		};
	};
	module crcl {
		module base {
			class PointTypeIce;
		};
	};
	module crcl {
		module base {
			enum CommandStateEnumTypeIce {
				CRCL_DONE=0,
				CRCL_ERROR=1,
				CRCL_WORKING=2,
				CRCL_READY=3
			};
		};
	};
	module testinlib {
		class TestClassIce {
			string name;
		};
	};
	module crcl {
		module base {
			class DataThingTypeIce {
				string name;
			};
		};
	};
	module crcl {
		module base {
			class CRCLProgramTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::MiddleCommandTypeIceSequence middleCommand;
				crcl::base::InitCanonTypeIce initCanon;
				crcl::base::EndCanonTypeIce endCanon;
			};
		};
	};
	module crcl {
		module base {
			class RotSpeedTypeIce  extends crcl::base::DataThingTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class RotAccelTypeIce  extends crcl::base::DataThingTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class CRCLCommandTypeIce  extends crcl::base::DataThingTypeIce {
				long commandID;
			};
		};
	};
	module crcl {
		module base {
			class MiddleCommandTypeIce  extends crcl::base::CRCLCommandTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class ActuateJointsTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::ActuateJointTypeIceSequence actuateJoint;
			};
		};
	};
	module crcl {
		module base {
			class PoseTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::PointTypeIce point;
				crcl::base::VectorTypeIce zAxis;
				crcl::base::VectorTypeIce xAxis;
			};
		};
	};
	module crcl {
		module base {
			class JointDetailsTypeIce  extends crcl::base::DataThingTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class JointSpeedAccelTypeIce  extends crcl::base::JointDetailsTypeIce {
				double jointAccel;
				double jointSpeed;
			};
		};
	};
	module crcl {
		module base {
			class TransSpeedTypeIce  extends crcl::base::DataThingTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class TransSpeedAbsoluteTypeIce  extends crcl::base::TransSpeedTypeIce {
				double setting;
			};
		};
	};
	module crcl {
		module base {
			class PoseToleranceTypeIce  extends crcl::base::DataThingTypeIce {
				double yPointTolerance;
				double xAxisTolerance;
				double zPointTolerance;
				double xPointTolerance;
				double zAxisTolerance;
			};
		};
	};
	module crcl {
		module base {
			class TransAccelTypeIce  extends crcl::base::DataThingTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class WrenchTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::VectorTypeIce moment;
				crcl::base::VectorTypeIce force;
			};
		};
	};
	module crcl {
		module base {
			class GripperStatusTypeIce  extends crcl::base::DataThingTypeIce {
				bool holdingObject;
				string gripperName;
			};
		};
	};
	module crcl {
		module base {
			class InitCanonTypeIce  extends crcl::base::CRCLCommandTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class MessageTypeIce  extends crcl::base::MiddleCommandTypeIce {
				string message;
			};
		};
	};
	module crcl {
		module base {
			class SetIntermediatePoseToleranceTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::PoseToleranceTypeIce tolerance;
			};
		};
	};
	module crcl {
		module base {
			class TransAccelAbsoluteTypeIce  extends crcl::base::TransAccelTypeIce {
				double setting;
			};
		};
	};
	module crcl {
		module base {
			class StopMotionTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::StopConditionEnumTypeIce stopCondition;
			};
		};
	};
	module crcl {
		module base {
			class VectorTypeIce  extends crcl::base::DataThingTypeIce {
				double j;
				double k;
				double i;
			};
		};
	};
	module crcl {
		module base {
			class SetEndEffectorTypeIce  extends crcl::base::MiddleCommandTypeIce {
				double setting;
			};
		};
	};
	module crcl {
		module base {
			class CloseToolChangerTypeIce  extends crcl::base::MiddleCommandTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class ConfigureJointReportsTypeIce  extends crcl::base::MiddleCommandTypeIce {
				bool resetAll;
				crcl::base::ConfigureJointReportTypeIceSequence configureJointReport;
			};
		};
	};
	module crcl {
		module base {
			class CRCLCommandInstanceTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::CRCLCommandTypeIce cRCLCommand;
				long programIndex;
				long programLength;
				string programFile;
			};
		};
	};
	module crcl {
		module base {
			class SetRotSpeedTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::RotSpeedTypeIce rotSpeed;
			};
		};
	};
	module crcl {
		module base {
			class ConfigureStatusReportTypeIce  extends crcl::base::MiddleCommandTypeIce {
				bool reportPoseStatus;
				bool reportSettingsStatus;
				bool reportJointStatuses;
				bool reportGripperStatus;
			};
		};
	};
	module crcl {
		module base {
			class SetTorqueUnitsTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::TorqueUnitEnumTypeIce unitName;
			};
		};
	};
	module crcl {
		module base {
			class JointLimitTypeIce  extends crcl::base::DataThingTypeIce {
				long jointNumber;
				double jointMaxTorqueOrForce;
				double jointMaxVelocity;
				double jointMaxPosition;
				double jointMinPosition;
			};
		};
	};
	module crcl {
		module base {
			class ConfigureJointReportTypeIce  extends crcl::base::DataThingTypeIce {
				long jointNumber;
				bool reportPosition;
				bool reportVelocity;
				bool reportTorqueOrForce;
			};
		};
	};
	module crcl {
		module base {
			class SetEndPoseToleranceTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::PoseToleranceTypeIce tolerance;
			};
		};
	};
	module crcl {
		module base {
			class ParameterSettingTypeIce  extends crcl::base::DataThingTypeIce {
				string parameterValue;
				string parameterName;
			};
		};
	};
	module crcl {
		module base {
			class SetTransSpeedTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::TransSpeedTypeIce transSpeed;
			};
		};
	};
	module crcl {
		module base {
			class JointStatusesTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::JointStatusTypeIceSequence jointStatus;
			};
		};
	};
	module crcl {
		module base {
			class ParallelGripperStatusTypeIce  extends crcl::base::GripperStatusTypeIce {
				double separation;
			};
		};
	};
	module crcl {
		module base {
			class SetRotAccelTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::RotAccelTypeIce rotAccel;
			};
		};
	};
	module crcl {
		module base {
			class SetAngleUnitsTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::AngleUnitEnumTypeIce unitName;
			};
		};
	};
	module crcl {
		module base {
			class CommandStatusTypeIce  extends crcl::base::DataThingTypeIce {
				long programIndex;
				long statusID;
				string stateDescription;
				long commandID;
				long programLength;
				crcl::base::CommandStateEnumTypeIce commandState;
				string programFile;
			};
		};
	};
	module crcl {
		module base {
			class PoseStatusTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::PoseTypeIce pose;
				crcl::base::TwistTypeIce twist;
				string configuration;
				crcl::base::WrenchTypeIce wrench;
			};
		};
	};
	module crcl {
		module base {
			class TransSpeedRelativeTypeIce  extends crcl::base::TransSpeedTypeIce {
				double fraction;
			};
		};
	};
	module crcl {
		module base {
			class SetForceUnitsTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::ForceUnitEnumTypeIce unitName;
			};
		};
	};
	module crcl {
		module base {
			class JointStatusTypeIce  extends crcl::base::DataThingTypeIce {
				double jointPosition;
				double jointVelocity;
				long jointNumber;
				double jointTorqueOrForce;
			};
		};
	};
	module crcl {
		module base {
			class VacuumGripperStatusTypeIce  extends crcl::base::GripperStatusTypeIce {
				bool isPowered;
			};
		};
	};
	module crcl {
		module base {
			class SetEndEffectorParametersTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::ParameterSettingTypeIceSequence parameterSetting;
			};
		};
	};
	module crcl {
		module base {
			class EndCanonTypeIce  extends crcl::base::CRCLCommandTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class CRCLStatusTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::GripperStatusTypeIce gripperStatus;
				crcl::base::JointStatusesTypeIce jointStatuses;
				crcl::base::PoseStatusTypeIce poseStatus;
				crcl::base::SettingsStatusTypeIce settingsStatus;
				crcl::base::CommandStatusTypeIce commandStatus;
			};
		};
	};
	module crcl {
		module base {
			class RotSpeedRelativeTypeIce  extends crcl::base::RotSpeedTypeIce {
				double fraction;
			};
		};
	};
	module crcl {
		module base {
			class RunProgramTypeIce  extends crcl::base::MiddleCommandTypeIce {
				string programText;
			};
		};
	};
	module crcl {
		module base {
			class RotAccelRelativeTypeIce  extends crcl::base::RotAccelTypeIce {
				double fraction;
			};
		};
	};
	module crcl {
		module base {
			class MoveThroughToTypeIce  extends crcl::base::MiddleCommandTypeIce {
				bool moveStraight;
				crcl::base::PoseTypeIceSequence waypoint;
				long numPositions;
			};
		};
	};
	module crcl {
		module base {
			class GetStatusTypeIce  extends crcl::base::MiddleCommandTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class TwistTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::VectorTypeIce linearVelocity;
				crcl::base::VectorTypeIce angularVelocity;
			};
		};
	};
	module crcl {
		module base {
			class MoveScrewTypeIce  extends crcl::base::MiddleCommandTypeIce {
				double turn;
				double axialDistanceFree;
				double axialDistanceScrew;
				crcl::base::PointTypeIce axisPoint;
				crcl::base::PoseTypeIce startPosition;
			};
		};
	};
	module crcl {
		module base {
			class RotAccelAbsoluteTypeIce  extends crcl::base::RotAccelTypeIce {
				double setting;
			};
		};
	};
	module crcl {
		module base {
			class SetMotionCoordinationTypeIce  extends crcl::base::MiddleCommandTypeIce {
				bool coordinated;
			};
		};
	};
	module crcl {
		module base {
			class ActuateJointTypeIce  extends crcl::base::DataThingTypeIce {
				double jointPosition;
				long jointNumber;
				crcl::base::JointDetailsTypeIce jointDetails;
			};
		};
	};
	module crcl {
		module base {
			class RotSpeedAbsoluteTypeIce  extends crcl::base::RotSpeedTypeIce {
				double setting;
			};
		};
	};
	module crcl {
		module base {
			class ThreeFingerGripperStatusTypeIce  extends crcl::base::GripperStatusTypeIce {
				double finger1Position;
				double finger2Position;
				double finger2Force;
				double finger1Force;
				double finger3Force;
				double finger3Position;
			};
		};
	};
	module crcl {
		module base {
			class SetRobotParametersTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::ParameterSettingTypeIceSequence parameterSetting;
			};
		};
	};
	module crcl {
		module base {
			class MoveToTypeIce  extends crcl::base::MiddleCommandTypeIce {
				bool moveStraight;
				crcl::base::PoseTypeIce endPosition;
			};
		};
	};
	module crcl {
		module base {
			class ObjectFactoryIce {
			};
		};
	};
	module crcl {
		module base {
			class PoseAndSetTypeIce  extends crcl::base::PoseTypeIce {
				crcl::base::TransSpeedTypeIce transSpeed;
				crcl::base::RotAccelTypeIce rotAccel;
				crcl::base::PoseToleranceTypeIce tolerance;
				bool coordinated;
				crcl::base::RotSpeedTypeIce rotSpeed;
				crcl::base::TransAccelTypeIce transAccel;
			};
		};
	};
	module crcl {
		module base {
			class SettingsStatusTypeIce  extends crcl::base::DataThingTypeIce {
				crcl::base::TransSpeedAbsoluteTypeIce transSpeedAbsolute;
				crcl::base::ForceUnitEnumTypeIce forceUnitName;
				crcl::base::TransAccelAbsoluteTypeIce transAccelAbsolute;
				crcl::base::ParameterSettingTypeIceSequence endEffectorParameterSetting;
				crcl::base::TorqueUnitEnumTypeIce torqueUnitName;
				double endEffectorSetting;
				crcl::base::RotAccelRelativeTypeIce rotAccelRelative;
				crcl::base::JointLimitTypeIceSequence jointLimits;
				crcl::base::PoseToleranceTypeIce intermediatePoseTolerance;
				bool motionCoordinated;
				crcl::base::PointTypeIce minCartesianLimit;
				crcl::base::RotSpeedAbsoluteTypeIce rotSpeedAbsolute;
				crcl::base::PointTypeIce maxCartesianLimit;
				crcl::base::LengthUnitEnumTypeIce lengthUnitName;
				crcl::base::PoseToleranceTypeIce poseTolerance;
				crcl::base::TransSpeedRelativeTypeIce transSpeedRelative;
				crcl::base::RotSpeedRelativeTypeIce rotSpeedRelative;
				crcl::base::TransAccelRelativeTypeIce transAccelRelative;
				crcl::base::AngleUnitEnumTypeIce angleUnitName;
				crcl::base::ParameterSettingTypeIceSequence robotParameterSetting;
				crcl::base::RotAccelAbsoluteTypeIce rotAccelAbsolute;
			};
		};
	};
	module crcl {
		module base {
			class TransAccelRelativeTypeIce  extends crcl::base::TransAccelTypeIce {
				double fraction;
			};
		};
	};
	module crcl {
		module base {
			class DwellTypeIce  extends crcl::base::MiddleCommandTypeIce {
				double dwellTime;
			};
		};
	};
	module crcl {
		module base {
			class SetLengthUnitsTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::LengthUnitEnumTypeIce unitName;
			};
		};
	};
	module crcl {
		module base {
			class OpenToolChangerTypeIce  extends crcl::base::MiddleCommandTypeIce {
			};
		};
	};
	module crcl {
		module base {
			class SetTransAccelTypeIce  extends crcl::base::MiddleCommandTypeIce {
				crcl::base::TransAccelTypeIce transAccel;
			};
		};
	};
	module crcl {
		module base {
			class JointForceTorqueTypeIce  extends crcl::base::JointDetailsTypeIce {
				double setting;
				double changeRate;
			};
		};
	};
	module crcl {
		module base {
			class PointTypeIce  extends crcl::base::DataThingTypeIce {
				double z;
				double y;
				double x;
			};
		};
	};
};
