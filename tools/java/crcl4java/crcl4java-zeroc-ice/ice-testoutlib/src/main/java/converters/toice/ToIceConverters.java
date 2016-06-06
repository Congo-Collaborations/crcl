package converters.toice;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import testinlib.TestClass;
import java2slice.testinlib.TestClassIce;
import crcl.base.DataThingType;
import java2slice.crcl.base.DataThingTypeIce;
import crcl.base.CRCLProgramType;
import java2slice.crcl.base.CRCLProgramTypeIce;
import crcl.base.RotSpeedType;
import java2slice.crcl.base.RotSpeedTypeIce;
import crcl.base.RotAccelType;
import java2slice.crcl.base.RotAccelTypeIce;
import crcl.base.CRCLCommandType;
import java2slice.crcl.base.CRCLCommandTypeIce;
import crcl.base.MiddleCommandType;
import java2slice.crcl.base.MiddleCommandTypeIce;
import crcl.base.ActuateJointsType;
import java2slice.crcl.base.ActuateJointsTypeIce;
import crcl.base.PoseType;
import java2slice.crcl.base.PoseTypeIce;
import crcl.base.JointDetailsType;
import java2slice.crcl.base.JointDetailsTypeIce;
import crcl.base.JointSpeedAccelType;
import java2slice.crcl.base.JointSpeedAccelTypeIce;
import crcl.base.TransSpeedType;
import java2slice.crcl.base.TransSpeedTypeIce;
import crcl.base.TransSpeedAbsoluteType;
import java2slice.crcl.base.TransSpeedAbsoluteTypeIce;
import crcl.base.PoseToleranceType;
import java2slice.crcl.base.PoseToleranceTypeIce;
import crcl.base.TransAccelType;
import java2slice.crcl.base.TransAccelTypeIce;
import crcl.base.WrenchType;
import java2slice.crcl.base.WrenchTypeIce;
import crcl.base.GripperStatusType;
import java2slice.crcl.base.GripperStatusTypeIce;
import crcl.base.InitCanonType;
import java2slice.crcl.base.InitCanonTypeIce;
import crcl.base.MessageType;
import java2slice.crcl.base.MessageTypeIce;
import crcl.base.AngleUnitEnumType;
import java2slice.crcl.base.AngleUnitEnumTypeIce;
import crcl.base.SetIntermediatePoseToleranceType;
import java2slice.crcl.base.SetIntermediatePoseToleranceTypeIce;
import crcl.base.TransAccelAbsoluteType;
import java2slice.crcl.base.TransAccelAbsoluteTypeIce;
import crcl.base.StopMotionType;
import java2slice.crcl.base.StopMotionTypeIce;
import crcl.base.VectorType;
import java2slice.crcl.base.VectorTypeIce;
import crcl.base.SetEndEffectorType;
import java2slice.crcl.base.SetEndEffectorTypeIce;
import crcl.base.CloseToolChangerType;
import java2slice.crcl.base.CloseToolChangerTypeIce;
import crcl.base.ForceUnitEnumType;
import java2slice.crcl.base.ForceUnitEnumTypeIce;
import crcl.base.TorqueUnitEnumType;
import java2slice.crcl.base.TorqueUnitEnumTypeIce;
import crcl.base.ConfigureJointReportsType;
import java2slice.crcl.base.ConfigureJointReportsTypeIce;
import crcl.base.CRCLCommandInstanceType;
import java2slice.crcl.base.CRCLCommandInstanceTypeIce;
import crcl.base.SetRotSpeedType;
import java2slice.crcl.base.SetRotSpeedTypeIce;
import crcl.base.ConfigureStatusReportType;
import java2slice.crcl.base.ConfigureStatusReportTypeIce;
import crcl.base.SetTorqueUnitsType;
import java2slice.crcl.base.SetTorqueUnitsTypeIce;
import crcl.base.JointLimitType;
import java2slice.crcl.base.JointLimitTypeIce;
import crcl.base.ConfigureJointReportType;
import java2slice.crcl.base.ConfigureJointReportTypeIce;
import crcl.base.SetEndPoseToleranceType;
import java2slice.crcl.base.SetEndPoseToleranceTypeIce;
import crcl.base.ParameterSettingType;
import java2slice.crcl.base.ParameterSettingTypeIce;
import crcl.base.SetTransSpeedType;
import java2slice.crcl.base.SetTransSpeedTypeIce;
import crcl.base.JointStatusesType;
import java2slice.crcl.base.JointStatusesTypeIce;
import crcl.base.ParallelGripperStatusType;
import java2slice.crcl.base.ParallelGripperStatusTypeIce;
import crcl.base.LengthUnitEnumType;
import java2slice.crcl.base.LengthUnitEnumTypeIce;
import crcl.base.SetRotAccelType;
import java2slice.crcl.base.SetRotAccelTypeIce;
import crcl.base.SetAngleUnitsType;
import java2slice.crcl.base.SetAngleUnitsTypeIce;
import crcl.base.CommandStatusType;
import java2slice.crcl.base.CommandStatusTypeIce;
import crcl.base.PoseStatusType;
import java2slice.crcl.base.PoseStatusTypeIce;
import crcl.base.TransSpeedRelativeType;
import java2slice.crcl.base.TransSpeedRelativeTypeIce;
import crcl.base.SetForceUnitsType;
import java2slice.crcl.base.SetForceUnitsTypeIce;
import crcl.base.JointStatusType;
import java2slice.crcl.base.JointStatusTypeIce;
import crcl.base.VacuumGripperStatusType;
import java2slice.crcl.base.VacuumGripperStatusTypeIce;
import crcl.base.SetEndEffectorParametersType;
import java2slice.crcl.base.SetEndEffectorParametersTypeIce;
import crcl.base.EndCanonType;
import java2slice.crcl.base.EndCanonTypeIce;
import crcl.base.CRCLStatusType;
import java2slice.crcl.base.CRCLStatusTypeIce;
import crcl.base.RotSpeedRelativeType;
import java2slice.crcl.base.RotSpeedRelativeTypeIce;
import crcl.base.RunProgramType;
import java2slice.crcl.base.RunProgramTypeIce;
import crcl.base.RotAccelRelativeType;
import java2slice.crcl.base.RotAccelRelativeTypeIce;
import crcl.base.MoveThroughToType;
import java2slice.crcl.base.MoveThroughToTypeIce;
import crcl.base.GetStatusType;
import java2slice.crcl.base.GetStatusTypeIce;
import crcl.base.TwistType;
import java2slice.crcl.base.TwistTypeIce;
import crcl.base.MoveScrewType;
import java2slice.crcl.base.MoveScrewTypeIce;
import crcl.base.RotAccelAbsoluteType;
import java2slice.crcl.base.RotAccelAbsoluteTypeIce;
import crcl.base.SetMotionCoordinationType;
import java2slice.crcl.base.SetMotionCoordinationTypeIce;
import crcl.base.ActuateJointType;
import java2slice.crcl.base.ActuateJointTypeIce;
import crcl.base.StopConditionEnumType;
import java2slice.crcl.base.StopConditionEnumTypeIce;
import crcl.base.RotSpeedAbsoluteType;
import java2slice.crcl.base.RotSpeedAbsoluteTypeIce;
import crcl.base.ThreeFingerGripperStatusType;
import java2slice.crcl.base.ThreeFingerGripperStatusTypeIce;
import crcl.base.SetRobotParametersType;
import java2slice.crcl.base.SetRobotParametersTypeIce;
import crcl.base.MoveToType;
import java2slice.crcl.base.MoveToTypeIce;
import crcl.base.ObjectFactory;
import java2slice.crcl.base.ObjectFactoryIce;
import crcl.base.PoseAndSetType;
import java2slice.crcl.base.PoseAndSetTypeIce;
import crcl.base.SettingsStatusType;
import java2slice.crcl.base.SettingsStatusTypeIce;
import crcl.base.TransAccelRelativeType;
import java2slice.crcl.base.TransAccelRelativeTypeIce;
import crcl.base.DwellType;
import java2slice.crcl.base.DwellTypeIce;
import crcl.base.SetLengthUnitsType;
import java2slice.crcl.base.SetLengthUnitsTypeIce;
import crcl.base.OpenToolChangerType;
import java2slice.crcl.base.OpenToolChangerTypeIce;
import crcl.base.SetTransAccelType;
import java2slice.crcl.base.SetTransAccelTypeIce;
import crcl.base.JointForceTorqueType;
import java2slice.crcl.base.JointForceTorqueTypeIce;
import crcl.base.PointType;
import java2slice.crcl.base.PointTypeIce;
import crcl.base.CommandStateEnumType;
import java2slice.crcl.base.CommandStateEnumTypeIce;

public class ToIceConverters {
	public static PoseTypeIce[] listOfPoseTypeToIce(List<PoseType> in) {
		PoseTypeIce newArray[]= new PoseTypeIce[in.size()];
		for(int i = 0; i < in.size(); i++) {
			newArray[i] = toIce(in.get(i));
		}
		return  newArray;
	}
	public static MiddleCommandTypeIce[] listOfMiddleCommandTypeToIce(List<MiddleCommandType> in) {
		MiddleCommandTypeIce newArray[]= new MiddleCommandTypeIce[in.size()];
		for(int i = 0; i < in.size(); i++) {
			newArray[i] = toIce(in.get(i));
		}
		return  newArray;
	}
	public static ConfigureJointReportTypeIce[] listOfConfigureJointReportTypeToIce(List<ConfigureJointReportType> in) {
		ConfigureJointReportTypeIce newArray[]= new ConfigureJointReportTypeIce[in.size()];
		for(int i = 0; i < in.size(); i++) {
			newArray[i] = toIce(in.get(i));
		}
		return  newArray;
	}
	public static JointLimitTypeIce[] listOfJointLimitTypeToIce(List<JointLimitType> in) {
		JointLimitTypeIce newArray[]= new JointLimitTypeIce[in.size()];
		for(int i = 0; i < in.size(); i++) {
			newArray[i] = toIce(in.get(i));
		}
		return  newArray;
	}
	public static JointStatusTypeIce[] listOfJointStatusTypeToIce(List<JointStatusType> in) {
		JointStatusTypeIce newArray[]= new JointStatusTypeIce[in.size()];
		for(int i = 0; i < in.size(); i++) {
			newArray[i] = toIce(in.get(i));
		}
		return  newArray;
	}
	public static ActuateJointTypeIce[] listOfActuateJointTypeToIce(List<ActuateJointType> in) {
		ActuateJointTypeIce newArray[]= new ActuateJointTypeIce[in.size()];
		for(int i = 0; i < in.size(); i++) {
			newArray[i] = toIce(in.get(i));
		}
		return  newArray;
	}
	public static ParameterSettingTypeIce[] listOfParameterSettingTypeToIce(List<ParameterSettingType> in) {
		ParameterSettingTypeIce newArray[]= new ParameterSettingTypeIce[in.size()];
		for(int i = 0; i < in.size(); i++) {
			newArray[i] = toIce(in.get(i));
		}
		return  newArray;
	}

	public static TestClassIce toIce(TestClass in) {
		return toIce(in, new TestClassIce());
	}

	public static TestClassIce toIce(TestClass in,TestClassIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out.name =  in.getName();
		return out;
	}

	public static DataThingTypeIce toIce(DataThingType in) {
		if(in instanceof PointType) {
			return toIce((PointType)in, new PointTypeIce());
		}
		else if(in instanceof JointForceTorqueType) {
			return toIce((JointForceTorqueType)in, new JointForceTorqueTypeIce());
		}
		else if(in instanceof SetTransAccelType) {
			return toIce((SetTransAccelType)in, new SetTransAccelTypeIce());
		}
		else if(in instanceof OpenToolChangerType) {
			return toIce((OpenToolChangerType)in, new OpenToolChangerTypeIce());
		}
		else if(in instanceof SetLengthUnitsType) {
			return toIce((SetLengthUnitsType)in, new SetLengthUnitsTypeIce());
		}
		else if(in instanceof DwellType) {
			return toIce((DwellType)in, new DwellTypeIce());
		}
		else if(in instanceof TransAccelRelativeType) {
			return toIce((TransAccelRelativeType)in, new TransAccelRelativeTypeIce());
		}
		else if(in instanceof SettingsStatusType) {
			return toIce((SettingsStatusType)in, new SettingsStatusTypeIce());
		}
		else if(in instanceof PoseAndSetType) {
			return toIce((PoseAndSetType)in, new PoseAndSetTypeIce());
		}
		else if(in instanceof MoveToType) {
			return toIce((MoveToType)in, new MoveToTypeIce());
		}
		else if(in instanceof SetRobotParametersType) {
			return toIce((SetRobotParametersType)in, new SetRobotParametersTypeIce());
		}
		else if(in instanceof ThreeFingerGripperStatusType) {
			return toIce((ThreeFingerGripperStatusType)in, new ThreeFingerGripperStatusTypeIce());
		}
		else if(in instanceof RotSpeedAbsoluteType) {
			return toIce((RotSpeedAbsoluteType)in, new RotSpeedAbsoluteTypeIce());
		}
		else if(in instanceof ActuateJointType) {
			return toIce((ActuateJointType)in, new ActuateJointTypeIce());
		}
		else if(in instanceof SetMotionCoordinationType) {
			return toIce((SetMotionCoordinationType)in, new SetMotionCoordinationTypeIce());
		}
		else if(in instanceof RotAccelAbsoluteType) {
			return toIce((RotAccelAbsoluteType)in, new RotAccelAbsoluteTypeIce());
		}
		else if(in instanceof MoveScrewType) {
			return toIce((MoveScrewType)in, new MoveScrewTypeIce());
		}
		else if(in instanceof TwistType) {
			return toIce((TwistType)in, new TwistTypeIce());
		}
		else if(in instanceof GetStatusType) {
			return toIce((GetStatusType)in, new GetStatusTypeIce());
		}
		else if(in instanceof MoveThroughToType) {
			return toIce((MoveThroughToType)in, new MoveThroughToTypeIce());
		}
		else if(in instanceof RotAccelRelativeType) {
			return toIce((RotAccelRelativeType)in, new RotAccelRelativeTypeIce());
		}
		else if(in instanceof RunProgramType) {
			return toIce((RunProgramType)in, new RunProgramTypeIce());
		}
		else if(in instanceof RotSpeedRelativeType) {
			return toIce((RotSpeedRelativeType)in, new RotSpeedRelativeTypeIce());
		}
		else if(in instanceof CRCLStatusType) {
			return toIce((CRCLStatusType)in, new CRCLStatusTypeIce());
		}
		else if(in instanceof EndCanonType) {
			return toIce((EndCanonType)in, new EndCanonTypeIce());
		}
		else if(in instanceof SetEndEffectorParametersType) {
			return toIce((SetEndEffectorParametersType)in, new SetEndEffectorParametersTypeIce());
		}
		else if(in instanceof VacuumGripperStatusType) {
			return toIce((VacuumGripperStatusType)in, new VacuumGripperStatusTypeIce());
		}
		else if(in instanceof JointStatusType) {
			return toIce((JointStatusType)in, new JointStatusTypeIce());
		}
		else if(in instanceof SetForceUnitsType) {
			return toIce((SetForceUnitsType)in, new SetForceUnitsTypeIce());
		}
		else if(in instanceof TransSpeedRelativeType) {
			return toIce((TransSpeedRelativeType)in, new TransSpeedRelativeTypeIce());
		}
		else if(in instanceof PoseStatusType) {
			return toIce((PoseStatusType)in, new PoseStatusTypeIce());
		}
		else if(in instanceof CommandStatusType) {
			return toIce((CommandStatusType)in, new CommandStatusTypeIce());
		}
		else if(in instanceof SetAngleUnitsType) {
			return toIce((SetAngleUnitsType)in, new SetAngleUnitsTypeIce());
		}
		else if(in instanceof SetRotAccelType) {
			return toIce((SetRotAccelType)in, new SetRotAccelTypeIce());
		}
		else if(in instanceof ParallelGripperStatusType) {
			return toIce((ParallelGripperStatusType)in, new ParallelGripperStatusTypeIce());
		}
		else if(in instanceof JointStatusesType) {
			return toIce((JointStatusesType)in, new JointStatusesTypeIce());
		}
		else if(in instanceof SetTransSpeedType) {
			return toIce((SetTransSpeedType)in, new SetTransSpeedTypeIce());
		}
		else if(in instanceof ParameterSettingType) {
			return toIce((ParameterSettingType)in, new ParameterSettingTypeIce());
		}
		else if(in instanceof SetEndPoseToleranceType) {
			return toIce((SetEndPoseToleranceType)in, new SetEndPoseToleranceTypeIce());
		}
		else if(in instanceof ConfigureJointReportType) {
			return toIce((ConfigureJointReportType)in, new ConfigureJointReportTypeIce());
		}
		else if(in instanceof JointLimitType) {
			return toIce((JointLimitType)in, new JointLimitTypeIce());
		}
		else if(in instanceof SetTorqueUnitsType) {
			return toIce((SetTorqueUnitsType)in, new SetTorqueUnitsTypeIce());
		}
		else if(in instanceof ConfigureStatusReportType) {
			return toIce((ConfigureStatusReportType)in, new ConfigureStatusReportTypeIce());
		}
		else if(in instanceof SetRotSpeedType) {
			return toIce((SetRotSpeedType)in, new SetRotSpeedTypeIce());
		}
		else if(in instanceof CRCLCommandInstanceType) {
			return toIce((CRCLCommandInstanceType)in, new CRCLCommandInstanceTypeIce());
		}
		else if(in instanceof ConfigureJointReportsType) {
			return toIce((ConfigureJointReportsType)in, new ConfigureJointReportsTypeIce());
		}
		else if(in instanceof CloseToolChangerType) {
			return toIce((CloseToolChangerType)in, new CloseToolChangerTypeIce());
		}
		else if(in instanceof SetEndEffectorType) {
			return toIce((SetEndEffectorType)in, new SetEndEffectorTypeIce());
		}
		else if(in instanceof VectorType) {
			return toIce((VectorType)in, new VectorTypeIce());
		}
		else if(in instanceof StopMotionType) {
			return toIce((StopMotionType)in, new StopMotionTypeIce());
		}
		else if(in instanceof TransAccelAbsoluteType) {
			return toIce((TransAccelAbsoluteType)in, new TransAccelAbsoluteTypeIce());
		}
		else if(in instanceof SetIntermediatePoseToleranceType) {
			return toIce((SetIntermediatePoseToleranceType)in, new SetIntermediatePoseToleranceTypeIce());
		}
		else if(in instanceof MessageType) {
			return toIce((MessageType)in, new MessageTypeIce());
		}
		else if(in instanceof InitCanonType) {
			return toIce((InitCanonType)in, new InitCanonTypeIce());
		}
		else if(in instanceof WrenchType) {
			return toIce((WrenchType)in, new WrenchTypeIce());
		}
		else if(in instanceof PoseToleranceType) {
			return toIce((PoseToleranceType)in, new PoseToleranceTypeIce());
		}
		else if(in instanceof TransSpeedAbsoluteType) {
			return toIce((TransSpeedAbsoluteType)in, new TransSpeedAbsoluteTypeIce());
		}
		else if(in instanceof JointSpeedAccelType) {
			return toIce((JointSpeedAccelType)in, new JointSpeedAccelTypeIce());
		}
		else if(in instanceof PoseType) {
			return toIce((PoseType)in, new PoseTypeIce());
		}
		else if(in instanceof ActuateJointsType) {
			return toIce((ActuateJointsType)in, new ActuateJointsTypeIce());
		}
		else if(in instanceof CRCLProgramType) {
			return toIce((CRCLProgramType)in, new CRCLProgramTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static DataThingTypeIce toIce(DataThingType in,DataThingTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out.name =  in.getName();
		return out;
	}

	public static CRCLProgramTypeIce toIce(CRCLProgramType in) {
		return toIce(in, new CRCLProgramTypeIce());
	}

	public static CRCLProgramTypeIce toIce(CRCLProgramType in,CRCLProgramTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (CRCLProgramTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.middleCommand =  listOfMiddleCommandTypeToIce(in.getMiddleCommand());
		out.initCanon =  toIce(in.getInitCanon());
		out.endCanon =  toIce(in.getEndCanon());
		return out;
	}

	public static RotSpeedTypeIce toIce(RotSpeedType in) {
		if(in instanceof RotSpeedAbsoluteType) {
			return toIce((RotSpeedAbsoluteType)in, new RotSpeedAbsoluteTypeIce());
		}
		else if(in instanceof RotSpeedRelativeType) {
			return toIce((RotSpeedRelativeType)in, new RotSpeedRelativeTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static RotSpeedTypeIce toIce(RotSpeedType in,RotSpeedTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (RotSpeedTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		return out;
	}

	public static RotAccelTypeIce toIce(RotAccelType in) {
		if(in instanceof RotAccelAbsoluteType) {
			return toIce((RotAccelAbsoluteType)in, new RotAccelAbsoluteTypeIce());
		}
		else if(in instanceof RotAccelRelativeType) {
			return toIce((RotAccelRelativeType)in, new RotAccelRelativeTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static RotAccelTypeIce toIce(RotAccelType in,RotAccelTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (RotAccelTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		return out;
	}

	public static CRCLCommandTypeIce toIce(CRCLCommandType in) {
		if(in instanceof SetTransAccelType) {
			return toIce((SetTransAccelType)in, new SetTransAccelTypeIce());
		}
		else if(in instanceof OpenToolChangerType) {
			return toIce((OpenToolChangerType)in, new OpenToolChangerTypeIce());
		}
		else if(in instanceof SetLengthUnitsType) {
			return toIce((SetLengthUnitsType)in, new SetLengthUnitsTypeIce());
		}
		else if(in instanceof DwellType) {
			return toIce((DwellType)in, new DwellTypeIce());
		}
		else if(in instanceof MoveToType) {
			return toIce((MoveToType)in, new MoveToTypeIce());
		}
		else if(in instanceof SetRobotParametersType) {
			return toIce((SetRobotParametersType)in, new SetRobotParametersTypeIce());
		}
		else if(in instanceof SetMotionCoordinationType) {
			return toIce((SetMotionCoordinationType)in, new SetMotionCoordinationTypeIce());
		}
		else if(in instanceof MoveScrewType) {
			return toIce((MoveScrewType)in, new MoveScrewTypeIce());
		}
		else if(in instanceof GetStatusType) {
			return toIce((GetStatusType)in, new GetStatusTypeIce());
		}
		else if(in instanceof MoveThroughToType) {
			return toIce((MoveThroughToType)in, new MoveThroughToTypeIce());
		}
		else if(in instanceof RunProgramType) {
			return toIce((RunProgramType)in, new RunProgramTypeIce());
		}
		else if(in instanceof EndCanonType) {
			return toIce((EndCanonType)in, new EndCanonTypeIce());
		}
		else if(in instanceof SetEndEffectorParametersType) {
			return toIce((SetEndEffectorParametersType)in, new SetEndEffectorParametersTypeIce());
		}
		else if(in instanceof SetForceUnitsType) {
			return toIce((SetForceUnitsType)in, new SetForceUnitsTypeIce());
		}
		else if(in instanceof SetAngleUnitsType) {
			return toIce((SetAngleUnitsType)in, new SetAngleUnitsTypeIce());
		}
		else if(in instanceof SetRotAccelType) {
			return toIce((SetRotAccelType)in, new SetRotAccelTypeIce());
		}
		else if(in instanceof SetTransSpeedType) {
			return toIce((SetTransSpeedType)in, new SetTransSpeedTypeIce());
		}
		else if(in instanceof SetEndPoseToleranceType) {
			return toIce((SetEndPoseToleranceType)in, new SetEndPoseToleranceTypeIce());
		}
		else if(in instanceof SetTorqueUnitsType) {
			return toIce((SetTorqueUnitsType)in, new SetTorqueUnitsTypeIce());
		}
		else if(in instanceof ConfigureStatusReportType) {
			return toIce((ConfigureStatusReportType)in, new ConfigureStatusReportTypeIce());
		}
		else if(in instanceof SetRotSpeedType) {
			return toIce((SetRotSpeedType)in, new SetRotSpeedTypeIce());
		}
		else if(in instanceof ConfigureJointReportsType) {
			return toIce((ConfigureJointReportsType)in, new ConfigureJointReportsTypeIce());
		}
		else if(in instanceof CloseToolChangerType) {
			return toIce((CloseToolChangerType)in, new CloseToolChangerTypeIce());
		}
		else if(in instanceof SetEndEffectorType) {
			return toIce((SetEndEffectorType)in, new SetEndEffectorTypeIce());
		}
		else if(in instanceof StopMotionType) {
			return toIce((StopMotionType)in, new StopMotionTypeIce());
		}
		else if(in instanceof SetIntermediatePoseToleranceType) {
			return toIce((SetIntermediatePoseToleranceType)in, new SetIntermediatePoseToleranceTypeIce());
		}
		else if(in instanceof MessageType) {
			return toIce((MessageType)in, new MessageTypeIce());
		}
		else if(in instanceof InitCanonType) {
			return toIce((InitCanonType)in, new InitCanonTypeIce());
		}
		else if(in instanceof ActuateJointsType) {
			return toIce((ActuateJointsType)in, new ActuateJointsTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static CRCLCommandTypeIce toIce(CRCLCommandType in,CRCLCommandTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (CRCLCommandTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		if(null != in.getCommandID()) {
			out.commandID =  in.getCommandID().longValue();
		}
		return out;
	}

	public static MiddleCommandTypeIce toIce(MiddleCommandType in) {
		if(in instanceof SetTransAccelType) {
			return toIce((SetTransAccelType)in, new SetTransAccelTypeIce());
		}
		else if(in instanceof OpenToolChangerType) {
			return toIce((OpenToolChangerType)in, new OpenToolChangerTypeIce());
		}
		else if(in instanceof SetLengthUnitsType) {
			return toIce((SetLengthUnitsType)in, new SetLengthUnitsTypeIce());
		}
		else if(in instanceof DwellType) {
			return toIce((DwellType)in, new DwellTypeIce());
		}
		else if(in instanceof MoveToType) {
			return toIce((MoveToType)in, new MoveToTypeIce());
		}
		else if(in instanceof SetRobotParametersType) {
			return toIce((SetRobotParametersType)in, new SetRobotParametersTypeIce());
		}
		else if(in instanceof SetMotionCoordinationType) {
			return toIce((SetMotionCoordinationType)in, new SetMotionCoordinationTypeIce());
		}
		else if(in instanceof MoveScrewType) {
			return toIce((MoveScrewType)in, new MoveScrewTypeIce());
		}
		else if(in instanceof GetStatusType) {
			return toIce((GetStatusType)in, new GetStatusTypeIce());
		}
		else if(in instanceof MoveThroughToType) {
			return toIce((MoveThroughToType)in, new MoveThroughToTypeIce());
		}
		else if(in instanceof RunProgramType) {
			return toIce((RunProgramType)in, new RunProgramTypeIce());
		}
		else if(in instanceof SetEndEffectorParametersType) {
			return toIce((SetEndEffectorParametersType)in, new SetEndEffectorParametersTypeIce());
		}
		else if(in instanceof SetForceUnitsType) {
			return toIce((SetForceUnitsType)in, new SetForceUnitsTypeIce());
		}
		else if(in instanceof SetAngleUnitsType) {
			return toIce((SetAngleUnitsType)in, new SetAngleUnitsTypeIce());
		}
		else if(in instanceof SetRotAccelType) {
			return toIce((SetRotAccelType)in, new SetRotAccelTypeIce());
		}
		else if(in instanceof SetTransSpeedType) {
			return toIce((SetTransSpeedType)in, new SetTransSpeedTypeIce());
		}
		else if(in instanceof SetEndPoseToleranceType) {
			return toIce((SetEndPoseToleranceType)in, new SetEndPoseToleranceTypeIce());
		}
		else if(in instanceof SetTorqueUnitsType) {
			return toIce((SetTorqueUnitsType)in, new SetTorqueUnitsTypeIce());
		}
		else if(in instanceof ConfigureStatusReportType) {
			return toIce((ConfigureStatusReportType)in, new ConfigureStatusReportTypeIce());
		}
		else if(in instanceof SetRotSpeedType) {
			return toIce((SetRotSpeedType)in, new SetRotSpeedTypeIce());
		}
		else if(in instanceof ConfigureJointReportsType) {
			return toIce((ConfigureJointReportsType)in, new ConfigureJointReportsTypeIce());
		}
		else if(in instanceof CloseToolChangerType) {
			return toIce((CloseToolChangerType)in, new CloseToolChangerTypeIce());
		}
		else if(in instanceof SetEndEffectorType) {
			return toIce((SetEndEffectorType)in, new SetEndEffectorTypeIce());
		}
		else if(in instanceof StopMotionType) {
			return toIce((StopMotionType)in, new StopMotionTypeIce());
		}
		else if(in instanceof SetIntermediatePoseToleranceType) {
			return toIce((SetIntermediatePoseToleranceType)in, new SetIntermediatePoseToleranceTypeIce());
		}
		else if(in instanceof MessageType) {
			return toIce((MessageType)in, new MessageTypeIce());
		}
		else if(in instanceof ActuateJointsType) {
			return toIce((ActuateJointsType)in, new ActuateJointsTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static MiddleCommandTypeIce toIce(MiddleCommandType in,MiddleCommandTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (MiddleCommandTypeIce) toIce((CRCLCommandType)in,(CRCLCommandTypeIce)out);
		return out;
	}

	public static ActuateJointsTypeIce toIce(ActuateJointsType in) {
		return toIce(in, new ActuateJointsTypeIce());
	}

	public static ActuateJointsTypeIce toIce(ActuateJointsType in,ActuateJointsTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ActuateJointsTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.actuateJoint =  listOfActuateJointTypeToIce(in.getActuateJoint());
		return out;
	}

	public static PoseTypeIce toIce(PoseType in) {
		if(in instanceof PoseAndSetType) {
			return toIce((PoseAndSetType)in, new PoseAndSetTypeIce());
		}
		else if(in instanceof PoseType) {
			return toIce((PoseType)in, new PoseTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static PoseTypeIce toIce(PoseType in,PoseTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (PoseTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.point =  toIce(in.getPoint());
		out.zAxis =  toIce(in.getZAxis());
		out.xAxis =  toIce(in.getXAxis());
		return out;
	}

	public static JointDetailsTypeIce toIce(JointDetailsType in) {
		if(in instanceof JointForceTorqueType) {
			return toIce((JointForceTorqueType)in, new JointForceTorqueTypeIce());
		}
		else if(in instanceof JointSpeedAccelType) {
			return toIce((JointSpeedAccelType)in, new JointSpeedAccelTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static JointDetailsTypeIce toIce(JointDetailsType in,JointDetailsTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (JointDetailsTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		return out;
	}

	public static JointSpeedAccelTypeIce toIce(JointSpeedAccelType in) {
		return toIce(in, new JointSpeedAccelTypeIce());
	}

	public static JointSpeedAccelTypeIce toIce(JointSpeedAccelType in,JointSpeedAccelTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (JointSpeedAccelTypeIce) toIce((JointDetailsType)in,(JointDetailsTypeIce)out);
		out.jointAccel =  in.getJointAccel().doubleValue();
		out.jointSpeed =  in.getJointSpeed().doubleValue();
		return out;
	}

	public static TransSpeedTypeIce toIce(TransSpeedType in) {
		if(in instanceof TransSpeedRelativeType) {
			return toIce((TransSpeedRelativeType)in, new TransSpeedRelativeTypeIce());
		}
		else if(in instanceof TransSpeedAbsoluteType) {
			return toIce((TransSpeedAbsoluteType)in, new TransSpeedAbsoluteTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static TransSpeedTypeIce toIce(TransSpeedType in,TransSpeedTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (TransSpeedTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		return out;
	}

	public static TransSpeedAbsoluteTypeIce toIce(TransSpeedAbsoluteType in) {
		return toIce(in, new TransSpeedAbsoluteTypeIce());
	}

	public static TransSpeedAbsoluteTypeIce toIce(TransSpeedAbsoluteType in,TransSpeedAbsoluteTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (TransSpeedAbsoluteTypeIce) toIce((TransSpeedType)in,(TransSpeedTypeIce)out);
		out.setting =  in.getSetting().doubleValue();
		return out;
	}

	public static PoseToleranceTypeIce toIce(PoseToleranceType in) {
		return toIce(in, new PoseToleranceTypeIce());
	}

	public static PoseToleranceTypeIce toIce(PoseToleranceType in,PoseToleranceTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (PoseToleranceTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.yPointTolerance =  in.getYPointTolerance().doubleValue();
		out.xAxisTolerance =  in.getXAxisTolerance().doubleValue();
		out.zPointTolerance =  in.getZPointTolerance().doubleValue();
		out.xPointTolerance =  in.getXPointTolerance().doubleValue();
		out.zAxisTolerance =  in.getZAxisTolerance().doubleValue();
		return out;
	}

	public static TransAccelTypeIce toIce(TransAccelType in) {
		if(in instanceof TransAccelRelativeType) {
			return toIce((TransAccelRelativeType)in, new TransAccelRelativeTypeIce());
		}
		else if(in instanceof TransAccelAbsoluteType) {
			return toIce((TransAccelAbsoluteType)in, new TransAccelAbsoluteTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static TransAccelTypeIce toIce(TransAccelType in,TransAccelTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (TransAccelTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		return out;
	}

	public static WrenchTypeIce toIce(WrenchType in) {
		return toIce(in, new WrenchTypeIce());
	}

	public static WrenchTypeIce toIce(WrenchType in,WrenchTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (WrenchTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.moment =  toIce(in.getMoment());
		out.force =  toIce(in.getForce());
		return out;
	}

	public static GripperStatusTypeIce toIce(GripperStatusType in) {
		if(in instanceof ThreeFingerGripperStatusType) {
			return toIce((ThreeFingerGripperStatusType)in, new ThreeFingerGripperStatusTypeIce());
		}
		else if(in instanceof VacuumGripperStatusType) {
			return toIce((VacuumGripperStatusType)in, new VacuumGripperStatusTypeIce());
		}
		else if(in instanceof ParallelGripperStatusType) {
			return toIce((ParallelGripperStatusType)in, new ParallelGripperStatusTypeIce());
		}
		throw new IllegalArgumentException(in+" is not of recognized type");
	}

	public static GripperStatusTypeIce toIce(GripperStatusType in,GripperStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (GripperStatusTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		if(null !=  in.isHoldingObject()) {
			out.holdingObject =  in.isHoldingObject().booleanValue();
		}
		out.gripperName =  in.getGripperName();
		return out;
	}

	public static InitCanonTypeIce toIce(InitCanonType in) {
		return toIce(in, new InitCanonTypeIce());
	}

	public static InitCanonTypeIce toIce(InitCanonType in,InitCanonTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (InitCanonTypeIce) toIce((CRCLCommandType)in,(CRCLCommandTypeIce)out);
		return out;
	}

	public static MessageTypeIce toIce(MessageType in) {
		return toIce(in, new MessageTypeIce());
	}

	public static MessageTypeIce toIce(MessageType in,MessageTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (MessageTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.message =  in.getMessage();
		return out;
	}

	public static AngleUnitEnumTypeIce toIce(AngleUnitEnumType in) {
		return AngleUnitEnumTypeIce.valueOf(in.ordinal());
	}

	public static SetIntermediatePoseToleranceTypeIce toIce(SetIntermediatePoseToleranceType in) {
		return toIce(in, new SetIntermediatePoseToleranceTypeIce());
	}

	public static SetIntermediatePoseToleranceTypeIce toIce(SetIntermediatePoseToleranceType in,SetIntermediatePoseToleranceTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetIntermediatePoseToleranceTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.tolerance =  toIce(in.getTolerance());
		return out;
	}

	public static TransAccelAbsoluteTypeIce toIce(TransAccelAbsoluteType in) {
		return toIce(in, new TransAccelAbsoluteTypeIce());
	}

	public static TransAccelAbsoluteTypeIce toIce(TransAccelAbsoluteType in,TransAccelAbsoluteTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (TransAccelAbsoluteTypeIce) toIce((TransAccelType)in,(TransAccelTypeIce)out);
		out.setting =  in.getSetting().doubleValue();
		return out;
	}

	public static StopMotionTypeIce toIce(StopMotionType in) {
		return toIce(in, new StopMotionTypeIce());
	}

	public static StopMotionTypeIce toIce(StopMotionType in,StopMotionTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (StopMotionTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.stopCondition =  toIce(in.getStopCondition());
		return out;
	}

	public static VectorTypeIce toIce(VectorType in) {
		return toIce(in, new VectorTypeIce());
	}

	public static VectorTypeIce toIce(VectorType in,VectorTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (VectorTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.j =  in.getJ().doubleValue();
		out.k =  in.getK().doubleValue();
		out.i =  in.getI().doubleValue();
		return out;
	}

	public static SetEndEffectorTypeIce toIce(SetEndEffectorType in) {
		return toIce(in, new SetEndEffectorTypeIce());
	}

	public static SetEndEffectorTypeIce toIce(SetEndEffectorType in,SetEndEffectorTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetEndEffectorTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.setting =  in.getSetting().doubleValue();
		return out;
	}

	public static CloseToolChangerTypeIce toIce(CloseToolChangerType in) {
		return toIce(in, new CloseToolChangerTypeIce());
	}

	public static CloseToolChangerTypeIce toIce(CloseToolChangerType in,CloseToolChangerTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (CloseToolChangerTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		return out;
	}

	public static ForceUnitEnumTypeIce toIce(ForceUnitEnumType in) {
		return ForceUnitEnumTypeIce.valueOf(in.ordinal());
	}

	public static TorqueUnitEnumTypeIce toIce(TorqueUnitEnumType in) {
		return TorqueUnitEnumTypeIce.valueOf(in.ordinal());
	}

	public static ConfigureJointReportsTypeIce toIce(ConfigureJointReportsType in) {
		return toIce(in, new ConfigureJointReportsTypeIce());
	}

	public static ConfigureJointReportsTypeIce toIce(ConfigureJointReportsType in,ConfigureJointReportsTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ConfigureJointReportsTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
			out.resetAll =  in.isResetAll();
		out.configureJointReport =  listOfConfigureJointReportTypeToIce(in.getConfigureJointReport());
		return out;
	}

	public static CRCLCommandInstanceTypeIce toIce(CRCLCommandInstanceType in) {
		return toIce(in, new CRCLCommandInstanceTypeIce());
	}

	public static CRCLCommandInstanceTypeIce toIce(CRCLCommandInstanceType in,CRCLCommandInstanceTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (CRCLCommandInstanceTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.cRCLCommand =  toIce(in.getCRCLCommand());
		if(null != in.getProgramIndex()) {
			out.programIndex =  in.getProgramIndex().longValue();
		}
		if(null != in.getProgramLength()) {
			out.programLength =  in.getProgramLength().longValue();
		}
		out.programFile =  in.getProgramFile();
		return out;
	}

	public static SetRotSpeedTypeIce toIce(SetRotSpeedType in) {
		return toIce(in, new SetRotSpeedTypeIce());
	}

	public static SetRotSpeedTypeIce toIce(SetRotSpeedType in,SetRotSpeedTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetRotSpeedTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.rotSpeed =  toIce(in.getRotSpeed());
		return out;
	}

	public static ConfigureStatusReportTypeIce toIce(ConfigureStatusReportType in) {
		return toIce(in, new ConfigureStatusReportTypeIce());
	}

	public static ConfigureStatusReportTypeIce toIce(ConfigureStatusReportType in,ConfigureStatusReportTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ConfigureStatusReportTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
			out.reportPoseStatus =  in.isReportPoseStatus();
			out.reportSettingsStatus =  in.isReportSettingsStatus();
			out.reportJointStatuses =  in.isReportJointStatuses();
			out.reportGripperStatus =  in.isReportGripperStatus();
		return out;
	}

	public static SetTorqueUnitsTypeIce toIce(SetTorqueUnitsType in) {
		return toIce(in, new SetTorqueUnitsTypeIce());
	}

	public static SetTorqueUnitsTypeIce toIce(SetTorqueUnitsType in,SetTorqueUnitsTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetTorqueUnitsTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.unitName =  toIce(in.getUnitName());
		return out;
	}

	public static JointLimitTypeIce toIce(JointLimitType in) {
		return toIce(in, new JointLimitTypeIce());
	}

	public static JointLimitTypeIce toIce(JointLimitType in,JointLimitTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (JointLimitTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		if(null != in.getJointNumber()) {
			out.jointNumber =  in.getJointNumber().longValue();
		}
		out.jointMaxTorqueOrForce =  in.getJointMaxTorqueOrForce().doubleValue();
		out.jointMaxVelocity =  in.getJointMaxVelocity().doubleValue();
		out.jointMaxPosition =  in.getJointMaxPosition().doubleValue();
		out.jointMinPosition =  in.getJointMinPosition().doubleValue();
		return out;
	}

	public static ConfigureJointReportTypeIce toIce(ConfigureJointReportType in) {
		return toIce(in, new ConfigureJointReportTypeIce());
	}

	public static ConfigureJointReportTypeIce toIce(ConfigureJointReportType in,ConfigureJointReportTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ConfigureJointReportTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		if(null != in.getJointNumber()) {
			out.jointNumber =  in.getJointNumber().longValue();
		}
			out.reportPosition =  in.isReportPosition();
			out.reportVelocity =  in.isReportVelocity();
			out.reportTorqueOrForce =  in.isReportTorqueOrForce();
		return out;
	}

	public static SetEndPoseToleranceTypeIce toIce(SetEndPoseToleranceType in) {
		return toIce(in, new SetEndPoseToleranceTypeIce());
	}

	public static SetEndPoseToleranceTypeIce toIce(SetEndPoseToleranceType in,SetEndPoseToleranceTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetEndPoseToleranceTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.tolerance =  toIce(in.getTolerance());
		return out;
	}

	public static ParameterSettingTypeIce toIce(ParameterSettingType in) {
		return toIce(in, new ParameterSettingTypeIce());
	}

	public static ParameterSettingTypeIce toIce(ParameterSettingType in,ParameterSettingTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ParameterSettingTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.parameterValue =  in.getParameterValue();
		out.parameterName =  in.getParameterName();
		return out;
	}

	public static SetTransSpeedTypeIce toIce(SetTransSpeedType in) {
		return toIce(in, new SetTransSpeedTypeIce());
	}

	public static SetTransSpeedTypeIce toIce(SetTransSpeedType in,SetTransSpeedTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetTransSpeedTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.transSpeed =  toIce(in.getTransSpeed());
		return out;
	}

	public static JointStatusesTypeIce toIce(JointStatusesType in) {
		return toIce(in, new JointStatusesTypeIce());
	}

	public static JointStatusesTypeIce toIce(JointStatusesType in,JointStatusesTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (JointStatusesTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.jointStatus =  listOfJointStatusTypeToIce(in.getJointStatus());
		return out;
	}

	public static ParallelGripperStatusTypeIce toIce(ParallelGripperStatusType in) {
		return toIce(in, new ParallelGripperStatusTypeIce());
	}

	public static ParallelGripperStatusTypeIce toIce(ParallelGripperStatusType in,ParallelGripperStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ParallelGripperStatusTypeIce) toIce((GripperStatusType)in,(GripperStatusTypeIce)out);
		out.separation =  in.getSeparation().doubleValue();
		return out;
	}

	public static LengthUnitEnumTypeIce toIce(LengthUnitEnumType in) {
		return LengthUnitEnumTypeIce.valueOf(in.ordinal());
	}

	public static SetRotAccelTypeIce toIce(SetRotAccelType in) {
		return toIce(in, new SetRotAccelTypeIce());
	}

	public static SetRotAccelTypeIce toIce(SetRotAccelType in,SetRotAccelTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetRotAccelTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.rotAccel =  toIce(in.getRotAccel());
		return out;
	}

	public static SetAngleUnitsTypeIce toIce(SetAngleUnitsType in) {
		return toIce(in, new SetAngleUnitsTypeIce());
	}

	public static SetAngleUnitsTypeIce toIce(SetAngleUnitsType in,SetAngleUnitsTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetAngleUnitsTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.unitName =  toIce(in.getUnitName());
		return out;
	}

	public static CommandStatusTypeIce toIce(CommandStatusType in) {
		return toIce(in, new CommandStatusTypeIce());
	}

	public static CommandStatusTypeIce toIce(CommandStatusType in,CommandStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (CommandStatusTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		if(null != in.getProgramIndex()) {
			out.programIndex =  in.getProgramIndex().longValue();
		}
		if(null != in.getStatusID()) {
			out.statusID =  in.getStatusID().longValue();
		}
		out.stateDescription =  in.getStateDescription();
		if(null != in.getCommandID()) {
			out.commandID =  in.getCommandID().longValue();
		}
		if(null != in.getProgramLength()) {
			out.programLength =  in.getProgramLength().longValue();
		}
		out.commandState =  toIce(in.getCommandState());
		out.programFile =  in.getProgramFile();
		return out;
	}

	public static PoseStatusTypeIce toIce(PoseStatusType in) {
		return toIce(in, new PoseStatusTypeIce());
	}

	public static PoseStatusTypeIce toIce(PoseStatusType in,PoseStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (PoseStatusTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.pose =  toIce(in.getPose());
		out.twist =  toIce(in.getTwist());
		out.configuration =  in.getConfiguration();
		out.wrench =  toIce(in.getWrench());
		return out;
	}

	public static TransSpeedRelativeTypeIce toIce(TransSpeedRelativeType in) {
		return toIce(in, new TransSpeedRelativeTypeIce());
	}

	public static TransSpeedRelativeTypeIce toIce(TransSpeedRelativeType in,TransSpeedRelativeTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (TransSpeedRelativeTypeIce) toIce((TransSpeedType)in,(TransSpeedTypeIce)out);
		out.fraction =  in.getFraction().doubleValue();
		return out;
	}

	public static SetForceUnitsTypeIce toIce(SetForceUnitsType in) {
		return toIce(in, new SetForceUnitsTypeIce());
	}

	public static SetForceUnitsTypeIce toIce(SetForceUnitsType in,SetForceUnitsTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetForceUnitsTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.unitName =  toIce(in.getUnitName());
		return out;
	}

	public static JointStatusTypeIce toIce(JointStatusType in) {
		return toIce(in, new JointStatusTypeIce());
	}

	public static JointStatusTypeIce toIce(JointStatusType in,JointStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (JointStatusTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.jointPosition =  in.getJointPosition().doubleValue();
		out.jointVelocity =  in.getJointVelocity().doubleValue();
		if(null != in.getJointNumber()) {
			out.jointNumber =  in.getJointNumber().longValue();
		}
		out.jointTorqueOrForce =  in.getJointTorqueOrForce().doubleValue();
		return out;
	}

	public static VacuumGripperStatusTypeIce toIce(VacuumGripperStatusType in) {
		return toIce(in, new VacuumGripperStatusTypeIce());
	}

	public static VacuumGripperStatusTypeIce toIce(VacuumGripperStatusType in,VacuumGripperStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (VacuumGripperStatusTypeIce) toIce((GripperStatusType)in,(GripperStatusTypeIce)out);
			out.isPowered =  in.isIsPowered();
		return out;
	}

	public static SetEndEffectorParametersTypeIce toIce(SetEndEffectorParametersType in) {
		return toIce(in, new SetEndEffectorParametersTypeIce());
	}

	public static SetEndEffectorParametersTypeIce toIce(SetEndEffectorParametersType in,SetEndEffectorParametersTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetEndEffectorParametersTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.parameterSetting =  listOfParameterSettingTypeToIce(in.getParameterSetting());
		return out;
	}

	public static EndCanonTypeIce toIce(EndCanonType in) {
		return toIce(in, new EndCanonTypeIce());
	}

	public static EndCanonTypeIce toIce(EndCanonType in,EndCanonTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (EndCanonTypeIce) toIce((CRCLCommandType)in,(CRCLCommandTypeIce)out);
		return out;
	}

	public static CRCLStatusTypeIce toIce(CRCLStatusType in) {
		return toIce(in, new CRCLStatusTypeIce());
	}

	public static CRCLStatusTypeIce toIce(CRCLStatusType in,CRCLStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (CRCLStatusTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.gripperStatus =  toIce(in.getGripperStatus());
		out.jointStatuses =  toIce(in.getJointStatuses());
		out.poseStatus =  toIce(in.getPoseStatus());
		out.settingsStatus =  toIce(in.getSettingsStatus());
		out.commandStatus =  toIce(in.getCommandStatus());
		return out;
	}

	public static RotSpeedRelativeTypeIce toIce(RotSpeedRelativeType in) {
		return toIce(in, new RotSpeedRelativeTypeIce());
	}

	public static RotSpeedRelativeTypeIce toIce(RotSpeedRelativeType in,RotSpeedRelativeTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (RotSpeedRelativeTypeIce) toIce((RotSpeedType)in,(RotSpeedTypeIce)out);
		out.fraction =  in.getFraction().doubleValue();
		return out;
	}

	public static RunProgramTypeIce toIce(RunProgramType in) {
		return toIce(in, new RunProgramTypeIce());
	}

	public static RunProgramTypeIce toIce(RunProgramType in,RunProgramTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (RunProgramTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.programText =  in.getProgramText();
		return out;
	}

	public static RotAccelRelativeTypeIce toIce(RotAccelRelativeType in) {
		return toIce(in, new RotAccelRelativeTypeIce());
	}

	public static RotAccelRelativeTypeIce toIce(RotAccelRelativeType in,RotAccelRelativeTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (RotAccelRelativeTypeIce) toIce((RotAccelType)in,(RotAccelTypeIce)out);
		out.fraction =  in.getFraction().doubleValue();
		return out;
	}

	public static MoveThroughToTypeIce toIce(MoveThroughToType in) {
		return toIce(in, new MoveThroughToTypeIce());
	}

	public static MoveThroughToTypeIce toIce(MoveThroughToType in,MoveThroughToTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (MoveThroughToTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
			out.moveStraight =  in.isMoveStraight();
		out.waypoint =  listOfPoseTypeToIce(in.getWaypoint());
		if(null != in.getNumPositions()) {
			out.numPositions =  in.getNumPositions().longValue();
		}
		return out;
	}

	public static GetStatusTypeIce toIce(GetStatusType in) {
		return toIce(in, new GetStatusTypeIce());
	}

	public static GetStatusTypeIce toIce(GetStatusType in,GetStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (GetStatusTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		return out;
	}

	public static TwistTypeIce toIce(TwistType in) {
		return toIce(in, new TwistTypeIce());
	}

	public static TwistTypeIce toIce(TwistType in,TwistTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (TwistTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.linearVelocity =  toIce(in.getLinearVelocity());
		out.angularVelocity =  toIce(in.getAngularVelocity());
		return out;
	}

	public static MoveScrewTypeIce toIce(MoveScrewType in) {
		return toIce(in, new MoveScrewTypeIce());
	}

	public static MoveScrewTypeIce toIce(MoveScrewType in,MoveScrewTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (MoveScrewTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.turn =  in.getTurn().doubleValue();
		out.axialDistanceFree =  in.getAxialDistanceFree().doubleValue();
		out.axialDistanceScrew =  in.getAxialDistanceScrew().doubleValue();
		out.axisPoint =  toIce(in.getAxisPoint());
		out.startPosition =  toIce(in.getStartPosition());
		return out;
	}

	public static RotAccelAbsoluteTypeIce toIce(RotAccelAbsoluteType in) {
		return toIce(in, new RotAccelAbsoluteTypeIce());
	}

	public static RotAccelAbsoluteTypeIce toIce(RotAccelAbsoluteType in,RotAccelAbsoluteTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (RotAccelAbsoluteTypeIce) toIce((RotAccelType)in,(RotAccelTypeIce)out);
		out.setting =  in.getSetting().doubleValue();
		return out;
	}

	public static SetMotionCoordinationTypeIce toIce(SetMotionCoordinationType in) {
		return toIce(in, new SetMotionCoordinationTypeIce());
	}

	public static SetMotionCoordinationTypeIce toIce(SetMotionCoordinationType in,SetMotionCoordinationTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetMotionCoordinationTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
			out.coordinated =  in.isCoordinated();
		return out;
	}

	public static ActuateJointTypeIce toIce(ActuateJointType in) {
		return toIce(in, new ActuateJointTypeIce());
	}

	public static ActuateJointTypeIce toIce(ActuateJointType in,ActuateJointTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ActuateJointTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.jointPosition =  in.getJointPosition().doubleValue();
		if(null != in.getJointNumber()) {
			out.jointNumber =  in.getJointNumber().longValue();
		}
		out.jointDetails =  toIce(in.getJointDetails());
		return out;
	}

	public static StopConditionEnumTypeIce toIce(StopConditionEnumType in) {
		return StopConditionEnumTypeIce.valueOf(in.ordinal());
	}

	public static RotSpeedAbsoluteTypeIce toIce(RotSpeedAbsoluteType in) {
		return toIce(in, new RotSpeedAbsoluteTypeIce());
	}

	public static RotSpeedAbsoluteTypeIce toIce(RotSpeedAbsoluteType in,RotSpeedAbsoluteTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (RotSpeedAbsoluteTypeIce) toIce((RotSpeedType)in,(RotSpeedTypeIce)out);
		out.setting =  in.getSetting().doubleValue();
		return out;
	}

	public static ThreeFingerGripperStatusTypeIce toIce(ThreeFingerGripperStatusType in) {
		return toIce(in, new ThreeFingerGripperStatusTypeIce());
	}

	public static ThreeFingerGripperStatusTypeIce toIce(ThreeFingerGripperStatusType in,ThreeFingerGripperStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (ThreeFingerGripperStatusTypeIce) toIce((GripperStatusType)in,(GripperStatusTypeIce)out);
		out.finger1Position =  in.getFinger1Position().doubleValue();
		out.finger2Position =  in.getFinger2Position().doubleValue();
		out.finger2Force =  in.getFinger2Force().doubleValue();
		out.finger1Force =  in.getFinger1Force().doubleValue();
		out.finger3Force =  in.getFinger3Force().doubleValue();
		out.finger3Position =  in.getFinger3Position().doubleValue();
		return out;
	}

	public static SetRobotParametersTypeIce toIce(SetRobotParametersType in) {
		return toIce(in, new SetRobotParametersTypeIce());
	}

	public static SetRobotParametersTypeIce toIce(SetRobotParametersType in,SetRobotParametersTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetRobotParametersTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.parameterSetting =  listOfParameterSettingTypeToIce(in.getParameterSetting());
		return out;
	}

	public static MoveToTypeIce toIce(MoveToType in) {
		return toIce(in, new MoveToTypeIce());
	}

	public static MoveToTypeIce toIce(MoveToType in,MoveToTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (MoveToTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
			out.moveStraight =  in.isMoveStraight();
		out.endPosition =  toIce(in.getEndPosition());
		return out;
	}

	public static ObjectFactoryIce toIce(ObjectFactory in) {
		return toIce(in, new ObjectFactoryIce());
	}

	public static ObjectFactoryIce toIce(ObjectFactory in,ObjectFactoryIce out) {
		if(null == out) {
			 return toIce(in);
		}
		return out;
	}

	public static PoseAndSetTypeIce toIce(PoseAndSetType in) {
		return toIce(in, new PoseAndSetTypeIce());
	}

	public static PoseAndSetTypeIce toIce(PoseAndSetType in,PoseAndSetTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (PoseAndSetTypeIce) toIce((PoseType)in,(PoseTypeIce)out);
		out.transSpeed =  toIce(in.getTransSpeed());
		out.rotAccel =  toIce(in.getRotAccel());
		out.tolerance =  toIce(in.getTolerance());
			out.coordinated =  in.isCoordinated();
		out.rotSpeed =  toIce(in.getRotSpeed());
		out.transAccel =  toIce(in.getTransAccel());
		return out;
	}

	public static SettingsStatusTypeIce toIce(SettingsStatusType in) {
		return toIce(in, new SettingsStatusTypeIce());
	}

	public static SettingsStatusTypeIce toIce(SettingsStatusType in,SettingsStatusTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SettingsStatusTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.transSpeedAbsolute =  toIce(in.getTransSpeedAbsolute());
		out.forceUnitName =  toIce(in.getForceUnitName());
		out.transAccelAbsolute =  toIce(in.getTransAccelAbsolute());
		out.endEffectorParameterSetting =  listOfParameterSettingTypeToIce(in.getEndEffectorParameterSetting());
		out.torqueUnitName =  toIce(in.getTorqueUnitName());
		out.endEffectorSetting =  in.getEndEffectorSetting().doubleValue();
		out.rotAccelRelative =  toIce(in.getRotAccelRelative());
		out.jointLimits =  listOfJointLimitTypeToIce(in.getJointLimits());
		out.intermediatePoseTolerance =  toIce(in.getIntermediatePoseTolerance());
		if(null !=  in.isMotionCoordinated()) {
			out.motionCoordinated =  in.isMotionCoordinated().booleanValue();
		}
		out.minCartesianLimit =  toIce(in.getMinCartesianLimit());
		out.rotSpeedAbsolute =  toIce(in.getRotSpeedAbsolute());
		out.maxCartesianLimit =  toIce(in.getMaxCartesianLimit());
		out.lengthUnitName =  toIce(in.getLengthUnitName());
		out.poseTolerance =  toIce(in.getPoseTolerance());
		out.transSpeedRelative =  toIce(in.getTransSpeedRelative());
		out.rotSpeedRelative =  toIce(in.getRotSpeedRelative());
		out.transAccelRelative =  toIce(in.getTransAccelRelative());
		out.angleUnitName =  toIce(in.getAngleUnitName());
		out.robotParameterSetting =  listOfParameterSettingTypeToIce(in.getRobotParameterSetting());
		out.rotAccelAbsolute =  toIce(in.getRotAccelAbsolute());
		return out;
	}

	public static TransAccelRelativeTypeIce toIce(TransAccelRelativeType in) {
		return toIce(in, new TransAccelRelativeTypeIce());
	}

	public static TransAccelRelativeTypeIce toIce(TransAccelRelativeType in,TransAccelRelativeTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (TransAccelRelativeTypeIce) toIce((TransAccelType)in,(TransAccelTypeIce)out);
		out.fraction =  in.getFraction().doubleValue();
		return out;
	}

	public static DwellTypeIce toIce(DwellType in) {
		return toIce(in, new DwellTypeIce());
	}

	public static DwellTypeIce toIce(DwellType in,DwellTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (DwellTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.dwellTime =  in.getDwellTime().doubleValue();
		return out;
	}

	public static SetLengthUnitsTypeIce toIce(SetLengthUnitsType in) {
		return toIce(in, new SetLengthUnitsTypeIce());
	}

	public static SetLengthUnitsTypeIce toIce(SetLengthUnitsType in,SetLengthUnitsTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetLengthUnitsTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.unitName =  toIce(in.getUnitName());
		return out;
	}

	public static OpenToolChangerTypeIce toIce(OpenToolChangerType in) {
		return toIce(in, new OpenToolChangerTypeIce());
	}

	public static OpenToolChangerTypeIce toIce(OpenToolChangerType in,OpenToolChangerTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (OpenToolChangerTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		return out;
	}

	public static SetTransAccelTypeIce toIce(SetTransAccelType in) {
		return toIce(in, new SetTransAccelTypeIce());
	}

	public static SetTransAccelTypeIce toIce(SetTransAccelType in,SetTransAccelTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (SetTransAccelTypeIce) toIce((MiddleCommandType)in,(MiddleCommandTypeIce)out);
		out.transAccel =  toIce(in.getTransAccel());
		return out;
	}

	public static JointForceTorqueTypeIce toIce(JointForceTorqueType in) {
		return toIce(in, new JointForceTorqueTypeIce());
	}

	public static JointForceTorqueTypeIce toIce(JointForceTorqueType in,JointForceTorqueTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (JointForceTorqueTypeIce) toIce((JointDetailsType)in,(JointDetailsTypeIce)out);
		out.setting =  in.getSetting().doubleValue();
		out.changeRate =  in.getChangeRate().doubleValue();
		return out;
	}

	public static PointTypeIce toIce(PointType in) {
		return toIce(in, new PointTypeIce());
	}

	public static PointTypeIce toIce(PointType in,PointTypeIce out) {
		if(null == out) {
			 return toIce(in);
		}
		out = (PointTypeIce) toIce((DataThingType)in,(DataThingTypeIce)out);
		out.z =  in.getZ().doubleValue();
		out.y =  in.getY().doubleValue();
		out.x =  in.getX().doubleValue();
		return out;
	}

	public static CommandStateEnumTypeIce toIce(CommandStateEnumType in) {
		return CommandStateEnumTypeIce.valueOf(in.ordinal());
	}
}
