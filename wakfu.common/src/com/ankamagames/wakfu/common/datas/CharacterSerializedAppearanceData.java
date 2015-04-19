package com.ankamagames.wakfu.common.datas;

import com.ankamagames.framework.kernel.core.common.serialization.BinarSerialPart;

public class CharacterSerializedAppearanceData {
	public byte sex;
	public byte skinColorIndex;
	public byte hairColorIndex;
	public byte pupilColorIndex;
	public byte skinColorFactor;
	public byte hairColorFactor;
	public byte clothIndex;
	public byte faceIndex;
	public short currentTitle;
	public BinarSerialPart m_binarPart;

	public CharacterSerializedAppearanceData() {
		
	}
}