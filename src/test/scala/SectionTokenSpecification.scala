import org.specs._
import org.specs.runner._

package mustache {
object SectionTokenSpecification extends SpecificationWithJUnit {

  "section token" should {

    "not render children with null context" in {
      SectionToken(false
                    ,"foo"
                    ,List(
                      StaticTextToken("bar")
                    )
      ).render(
        null, Map(), new StringBuilder
      ).toString must be equalTo("")
    }

    "handle boolean context" in {
      val token = SectionToken(
                    false
                    ,"foo"
                    ,List(StaticTextToken("bar"))
                  )

      token.render(
        Map("foo"->false), Map(), new StringBuilder
      ).toString must be equalTo("")
      token.render(
        Map("foo"->true), Map(), new StringBuilder
      ).toString must be equalTo("bar")
    }

    "handle list context" in {
      val token = SectionToken(
                    false
                    ,"foo"
                    ,List(
                      StaticTextToken("bar:")
                      ,EscapedToken("value")
                      ,StaticTextToken(",")
                    )
                  )
      token.render(
        Map("foo"->List()), Map(), new StringBuilder
      ).toString must be equalTo("")
      token.render(
        Map("foo"->List(
                    Map("value"->1)
                    ,Map("value"->2)
                    ,Map("value"->3)
                    ))
        , Map(), new StringBuilder
      ).toString must be equalTo("bar:1,bar:2,bar:3,")    
    }

    "handle inverse null conext" in {
      SectionToken(true
                    ,"foo"
                    ,List(
                      StaticTextToken("bar")
                    )
      ).render(
        null, Map(), new StringBuilder
      ).toString must be equalTo("bar")
    }

    "handle inverse boolean context" in {
      val token = SectionToken(
                    true
                    ,"foo"
                    ,List(StaticTextToken("bar"))
                  )

      token.render(
        Map("foo"->false), Map(), new StringBuilder
      ).toString must be equalTo("bar")
      token.render(
        Map("foo"->true), Map(), new StringBuilder
      ).toString must be equalTo("")
    }

    "handle inverse list context" in {
      val token = SectionToken(
                    true
                    ,"foo"
                    ,List(StaticTextToken("list is empty"))
                  )
      token.render(
        Map("foo"->List()), Map(), new StringBuilder
      ).toString must be equalTo("list is empty")
      token.render(
        Map("foo"->List(
                    Map("value"->1)
                    ,Map("value"->2)
                    ,Map("value"->3)
                    ))
        , Map(), new StringBuilder
      ).toString must be equalTo("")
    }

  }

}
}