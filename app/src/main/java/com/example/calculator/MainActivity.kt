package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        buttonControl(binding)
    }

    fun buttonControl(binding: ActivityMainBinding){
        binding.buttonOpenBracket.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "("
        }
        binding.buttonCloseBracket.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + ")"
        }
        binding.buttonSeven.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "7"
        }
        binding.buttonEight.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "8"
        }
        binding.buttonNine.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "9"
        }
        binding.buttonFour.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "4"
        }
        binding.buttonFive.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "5"
        }
        binding.buttonSix.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "6"
        }
        binding.buttonOne.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "1"
        }
        binding.buttonTwo.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "2"
        }
        binding.buttonThree.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "3"
        }
        binding.buttonZero.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "0"
        }
        binding.buttonDelete.setOnClickListener {view: View ->
            val string = binding.txtvInput.text.toString()
            if(string.isNotEmpty()){
                binding.txtvInput.text = string.substring(0, string.length - 1)
            }
        }
        binding.buttonAddition.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "+"
        }
        binding.buttonSubtraction.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "-"
        }
        binding.buttonMultiplication.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "x"
        }
        binding.buttonDivision.setOnClickListener {view: View ->
            binding.txtvInput.text = binding.txtvInput.text.toString() + "/"
        }
        binding.buttonClear.setOnClickListener {view: View ->
            binding.txtvInput.text = ""
            binding.txtvOutput.text = ""
        }

        binding.buttonEqual.setOnClickListener { view: View ->
            if(binding.txtvInput.text.isNotEmpty()){
                calculate(binding)
            }
        }
    }

    /**
     * Do thời gian nên em mới xử lý được với số có 1 chữ số.
     *
     * Số có 2 chữ số trở lên xử lý phức tạp hơn chút,
     * nhưng phần logic tính toán vẫn giữ nguyên.
     *
     * Em cảm ơn!
     * **/
    fun calculate(binding: ActivityMainBinding){
        val stack = ArrayList<Char>()
        val queue = ArrayList<Char>()
        val arrResult = ArrayList<Int>()
        var temp = 0

        val string = binding.txtvInput.text.toString()

        val lenLoop = binding.txtvInput.length() - 1

        if(!string.contains('(') || !string.contains(')') || !string.contains('(') && !string.contains(')')){
            for(i in 0..lenLoop){
                if(string[i].equals('0') || string[i].equals('1') || string[i].equals('2') || string[i].equals('3') || string[i].equals('4') || string[i].equals('5') || string[i].equals('6') || string[i].equals('7') || string[i].equals('8') || string[i].equals('9')){
                    queue.add(string[i])
                }
                if(string[i].equals('+')){
                    if(stack.isEmpty()){
                        stack.add(string[i])
                    }else{
                        if(stack.size < 3){
                            queue.add(stack[stack.size - 1])
                            stack.removeAt(stack.size - 1)
                            stack.add('+')
                        }else {
                            for(k in 0 until stack.size - 1){
                                queue.add(stack[k])
                                stack.removeAt(k)
                            }
                            stack.add('+')
                        }
                    }
                }
                if(string[i].equals('-')){
                    if(stack.isEmpty()){
                        stack.add(string[i])
                    }else{
                        if(stack.size < 3){
                            queue.add(stack[stack.size - 1])
                            stack.removeAt(stack.size - 1)
                            stack.add('-')
                        }else {
                            for(k in 0 until stack.size - 1){
                                queue.add(stack[k])
                                stack.removeAt(k)
                            }
                            stack.add('-')
                        }
                    }
                }
                if(string[i].equals('x')){
                    stack.add(string[i])
                }
                if(string[i].equals('/')){
                    stack.add(string[i])
                }
            }
            if(!stack.isEmpty()){
                for(i in stack.size - 1 downTo 0){
                    queue.add(stack[i])
                    stack.removeAt(i)
                }
            }
        }else{
            for(i in 0..lenLoop){
                if(string[i].equals('0') || string[i].equals('1') || string[i].equals('2') || string[i].equals('3') || string[i].equals('4') || string[i].equals('5') || string[i].equals('6') || string[i].equals('7') || string[i].equals('8') || string[i].equals('9')){
                    queue.add(string[i])
                }
                if(string[i].equals('(')){
                    stack.add(string[i])
                }
                if(string[i].equals(')')){
                    val begin = stack.indexOf('(') + 1
                    val end = stack.size - 1
                    for(n in end downTo begin) {
                        queue.add(stack[n])
                        stack.removeAt(n)
                    }
                    queue.remove('(')
                }
                if(string[i].equals('+')){
                    if(stack.isEmpty()){
                        stack.add(string[i])
                    }else{
                        if(stack.size < 3){
                            queue.add(stack[stack.size - 1])
                            stack.removeAt(stack.size - 1)
                            stack.add('+')
                        }else {
                            for(k in 0 until stack.size - 1){
                                queue.add(stack[k])
                                stack.removeAt(k)
                            }
                            stack.add('+')
                        }
                    }
                }
                if(string[i].equals('-')){
                    if(stack.isEmpty()){
                        stack.add(string[i])
                    }else{
                        if(stack.size < 3){
                            queue.add(stack[stack.size - 1])
                            stack.removeAt(stack.size - 1)
                            stack.add('-')
                        }else {
                            for(k in 0 until stack.size - 1){
                                queue.add(stack[k])
                                stack.removeAt(k)
                            }
                            stack.add('-')
                        }
                    }
                }
                if(string[i].equals('x')){
                    stack.add(string[i])
                }
                if(string[i].equals('/')){
                    stack.add(string[i])
                }
            }
            if(!stack.isEmpty()){
                for(i in stack.size - 1 downTo 0){
                    queue.add(stack[i])
                    stack.removeAt(i)
                }
            }
        }

        for(i in 0 until queue.size){
            if(queue[i] != '+' && queue[i] != '-' && queue[i] != 'x' && queue[i] != '/'){
                arrResult.add(queue[i].toInt() - 48)
            }else {
                if(queue[i] == '+'){
                    temp = arrResult[arrResult.size - 1] + arrResult[arrResult.size - 2]
                    arrResult.removeAt(arrResult.size - 2)
                    arrResult.removeAt(arrResult.size - 1)
                    arrResult.add(temp)
                }
                if(queue[i] == '-'){
                    temp = arrResult[arrResult.size - 2] - arrResult[arrResult.size - 1]
                    arrResult.removeAt(arrResult.size - 2)
                    arrResult.removeAt(arrResult.size - 1)
                    arrResult.add(temp)
                }
                if(queue[i] == 'x'){
                    temp = arrResult[arrResult.size - 1] * arrResult[arrResult.size - 2]
                    arrResult.removeAt(arrResult.size - 2)
                    arrResult.removeAt(arrResult.size - 1)
                    arrResult.add(temp)
                }
                if(queue[i] == '/'){
                    temp = arrResult[arrResult.size - 1] % arrResult[arrResult.size - 2]
                    arrResult.removeAt(arrResult.size - 2)
                    arrResult.removeAt(arrResult.size - 1)
                    arrResult.add(temp)
                }
            }
        }

        binding.txtvOutput.text = arrResult[0].toString()

        Toast.makeText(this, ""+queue, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, ""+stack, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, ""+arrResult, Toast.LENGTH_SHORT).show()
    }
}
