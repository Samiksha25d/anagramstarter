/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary
{

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    List<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet = new HashSet<String>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    HashMap<Integer, ArrayList<String>> sizeToWord = new HashMap<Integer, ArrayList<String>>();
    int Default_Length = DEFAULT_WORD_LENGTH;
    String word = null;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortWord = sortstring(word);
            if (lettersToWord.containsKey(sortWord)) {
                ArrayList<String> sortal = lettersToWord.get(sortWord);
                sortal.add(word);
                lettersToWord.put(sortWord, sortal);
            } else {
                ArrayList<String> sortal = new ArrayList();
                sortal.add(word);
                lettersToWord.put(sortWord, sortal);
            }
            if ((sizeToWord.containsKey(word.length()))) {
                ArrayList<String> sortal = sizeToWord.get(word.length());
                sortal.add(word);
                sizeToWord.put(word.length(), sortal);
            } else {
                ArrayList<String> sortal = new ArrayList();
                sortal.add(word);
                sizeToWord.put(word.length(), sortal);
            }
        }
    }

    String sortstring(String s)
    {
        char[] ch=s.toCharArray();
        Arrays.sort(ch);
        String sortedWord=new String(ch);
        return sortedWord;
    }

    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && !word.contains(base))
            return true;
        else
            return false;
    }

    public List<String> getAnagrams(String targetWord) {
        String sortedWord = sortstring(targetWord);
        ArrayList<String> result = new ArrayList<String>();
        if (lettersToWord.containsKey(sortedWord)) {
            result=lettersToWord.get(sortedWord);
            result.remove(targetWord);
        }

        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> temp;
        ArrayList<String> result = new ArrayList<String>();
        for (char alpha = 'a'; alpha <= 'z'; alpha++) {
            String target = word + alpha;
            String sortedWord = sortstring(target);
            if (lettersToWord.containsKey(sortedWord)) {
                temp = lettersToWord.get(sortedWord);
                for (int i = 0; i < temp.size(); i++)
                    if (!(temp.get(i).contains(word)))
                        result.add(temp.get(i));
            }
        }
        return result;
    }

    public String pickGoodStarterWord()
    {
        String d="";
        while(true)
        {
            ArrayList<String> temp=sizeToWord.get(Default_Length);
            d=temp.get((random.nextInt(temp.size())));
            List<String> arr=getAnagramsWithOneMoreLetter(d);
            if(arr.size()>=MIN_NUM_ANAGRAMS)
                break;
        }
        Default_Length++;


        return d;
    }
}
