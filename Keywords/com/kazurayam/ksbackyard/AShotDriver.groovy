package com.kazurayam.ksbackyard

import javax.imageio.ImageIO

import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import ru.yandex.qatools.ashot.comparison.ImageDiff
import com.kms.katalon.core.util.KeywordUtil

public class AShotDriver {

	@Keyword
	static void takeEntirePage(WebDriver webDriver, File file) {
		Screenshot screenshot = new AShot().
				shootingStrategy(ShootingStrategies.viewportPasting(100)).
				takeScreenshot(webDriver)
		ImageIO.write(screenshot.getImage(), "PNG", file)
	}

	@Keyword
	static boolean hasSignificantDiff(ImageDiff diff, Float criteriaPercent) {
		boolean hasDiff = diff.hasDiff()
		int diffSize = diff.getDiffSize()
		int area = diff.getMarkedImage().getWidth() * diff.getMarkedImage().getHeight()
		Float diffRatio = diff.getDiffSize() / area * 100
		//
		StringBuilder sb = new StringBuilder()
		sb.append("hasDiff is ${hasDiff}, ")
		sb.append("diffSize is ${diffSize}, ")
		sb.append("area is ${area}, ")
		sb.append("diffRatio is ${diffRatio}%")
		WebUI.comment(">>> " + sb.toString())
		//
		if (diffRatio > criteriaPercent) {
			KeywordUtil.markFailed("diffRatio = ${diffRatio} is exceeding criteria = ${criteriaPercent}")
		}
	}
}
