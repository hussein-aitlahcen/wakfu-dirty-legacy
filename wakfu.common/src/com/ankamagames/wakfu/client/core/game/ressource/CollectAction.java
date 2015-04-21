package com.ankamagames.wakfu.client.core.game.ressource;

import com.ankamagames.framework.ai.criteria.antlrcriteria.SimpleCriterion;
import com.ankamagames.wakfu.common.game.craft.collect.AbstractCollectAction;
import com.ankamagames.wakfu.common.game.craft.collect.ConsumableInfo;

public class CollectAction extends AbstractCollectAction {

	public CollectAction(int actionId, int craftId, int levelMin,
			int nbPlayerMin, int collectDuration, int visualId,
			SimpleCriterion criterion, int resourceNextIndex,
			ConsumableInfo consumableInfo) {
		super(actionId, craftId, levelMin, nbPlayerMin, collectDuration, visualId,
				criterion, resourceNextIndex, consumableInfo);
	}
}
