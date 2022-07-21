package com.divyansh.spannablestringdemo

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import androidx.core.text.set
import com.divyansh.spannablestringdemo.databinding.ActivityMainBinding
import java.util.regex.Pattern
import kotlin.Result.Companion.success

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textOneSpan()
        textTwoSpan()
        textThreeSpan()
    }

    //Text Three
    private fun textThreeSpan() {
        val spannableString = SpannableString("Here is my first link: https://google.com.\nHere is my second link: https://android-developers.googleblog.com/")
        val urlPattern: Pattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )
        try{
            //val str = SpannableString(spannableString)
            val matcher = urlPattern.matcher(spannableString)
            var matchStart: Int
            var matchEnd: Int

            while (matcher.find()){
                matchStart = matcher.start(1)
                matchEnd = matcher.end()

                var url = spannableString.substring(matchStart, matchEnd)
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "https://$url"

                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                spannableString.setSpan(clickableSpan, matchStart, matchEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.tv3.text = spannableString
            binding.tv3.movementMethod = LinkMovementMethod.getInstance()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    //Text Two
    private fun textTwoSpan() {
        val spannableString = SpannableString("Bold-Italic text")
        spannableString.setSpan(RelativeSizeSpan(2f), 0, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        //Bold, Italic
        spannableString.setSpan(StyleSpan(Typeface.BOLD_ITALIC), 0, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        //Change color of textView
        spannableString.setSpan(ForegroundColorSpan(Color.BLACK), 0, spannableString.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        binding.tv2.text = spannableString
    }

    //Text One
    private fun textOneSpan() {
        val spannableString = SpannableStringBuilder(" Hi , Divyansh Verma.")
        //Foreground color
        spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

        //Background color
        spannableString.setSpan(BackgroundColorSpan(Color.LTGRAY), 0, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        //SpannableString append
        spannableString.append(" Welcome")
        binding.tv1.text = spannableString

    }
}