package nl.qsight.chainlink;

import nl.qsight.chainlink.ChainLink;
import nl.qsight.links.fields.IdentityLink;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestChainLink {

    private ChainLink link;

    @Before
    public void setUp() {
        this.link = new IdentityLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test(expected = AssertionError.class)
    public void testSelfLink() {
        // A self-link must not occur since it will result into infinite loops
        this.link.setNextLink(this.link);
    }

    @Test
    public void testNextLink() {
        ChainLink otherLink = new IdentityLink();
        this.link.setNextLink(otherLink);
        assertEquals(otherLink, this.link.getNextLink());
        assertTrue(this.link.hasNextLink());
    }

}
