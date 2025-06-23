package testCases;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import pageObjects.DashBoard;
import testBase.base;

public class DashBoard_testcase001 extends base {

	@Test
	public void tc_dashboard_in_draft_01() {

		loginToCLM(); // ✅ Login first

		DashBoard dash = new DashBoard(driver);

		// ✅ 1. Capture count before clicking
		String draftCount = dash.getDraftScorecardCount();
		System.out.println("🔢 Draft Count = " + draftCount);

		// ✅ 2. Click draft to navigate
		dash.clickDraftStatus();

		// ✅ 3. Get listing count after page loads
		String listingCount = dash.getContractsListingCount();
		System.out.println("📋 Listing Count = " + listingCount);

		// ✅ 4. Assertion
		assertEquals(draftCount, listingCount, "❌ Counts do not match!");

		// Optional manual print
		if (draftCount.equals(listingCount)) {
			System.out.println("✅ Test Passed: Counts match.");
		} else {
			System.out.println("❌ Test Failed: Mismatch in counts.");
		}
	}
}
