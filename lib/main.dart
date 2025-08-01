import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:audioplayers/audioplayers.dart';

void main() => runApp(YogaApp());

class YogaApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Yoga Flow',
      debugShowCheckedModeBanner: false,
      home: SplashScreen(),
    );
  }
}
// todo splash screen
class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();

    // 3 seconds baad home screen pe jao
    Future.delayed(Duration(seconds: 3), () {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => YogaScreen()),
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.green[100],
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.self_improvement, size: 100, color: Colors.green[700]),
            SizedBox(height: 20),
            Text(
              'Yoga Flow',
              style: TextStyle(
                fontSize: 28,
                fontWeight: FontWeight.bold,
                color: Colors.green[800],
              ),
            ),
          ],
        ),
      ),
    );
  }
}



// todo home screen
class YogaScreen extends StatefulWidget {
  @override
  _YogaScreenState createState() => _YogaScreenState();
}

class _YogaScreenState extends State<YogaScreen> {
  List<dynamic> sequence = [];
  Map<String, dynamic> assets = {};
  int currentSegmentIndex = 0;
  int currentScriptIndex = 0;
  bool isLoading = true;
  final AudioPlayer audioPlayer = AudioPlayer();

  @override
  void initState() {
    super.initState();
    loadData();
  }

  Future<void> loadData() async {
    try {
      final String jsonString = await rootBundle.loadString('assets/poses.json');
      final data = json.decode(jsonString);

      setState(() {
        sequence = data['sequence'];
        assets = data['assets'];
        isLoading = false;
        currentSegmentIndex = 0;
        currentScriptIndex = 0;
      });

      playCurrentSegmentAudio();
    } catch (e) {
      print('Error loading data: $e');
    }
  }

  void playCurrentSegmentAudio() async {
    if (currentSegmentIndex >= sequence.length) return;

    final segment = sequence[currentSegmentIndex];
    final String audioRef = segment['audioRef'];
    final String? audioFile = assets['audio'][audioRef];

    if (audioFile != null) {
      try {
        await audioPlayer.stop();
        await audioPlayer.play(AssetSource('audio/$audioFile'));
        print("✅ Playing: audio/$audioFile");
      } catch (e) {
        print('Failed to play audio: $e');
      }
    } else {
      print('Audio file not found for key: $audioRef');
    }
  }

  void nextPose() {
    if (currentSegmentIndex < sequence.length - 1) {
      setState(() {
        currentSegmentIndex++;
        currentScriptIndex = 0;
      });
      playCurrentSegmentAudio();
    } else {
      showDialog(
        context: context,
        builder: (_) => AlertDialog(
          title: Text("Session Complete!"),
          content: Text("Do you want to restart the session?"),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: Text("No"),
            ),
            TextButton(
              onPressed: () {
                Navigator.pop(context);
                setState(() {
                  currentSegmentIndex = 0;
                  currentScriptIndex = 0;
                });
                playCurrentSegmentAudio();
              },
              child: Text("Restart"),
            ),
          ],
        ),
      );
    }
  }

  @override
  void dispose() {
    audioPlayer.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return Scaffold(
        body: Center(child: CircularProgressIndicator()),
      );
    }

    final segment = sequence[currentSegmentIndex];
    final script = segment['script'];
    final current = script != null && script.isNotEmpty
        ? script[currentScriptIndex]
        : null;

    final String? imageKey = current?['imageRef'];
    final String? imageFile = imageKey != null ? assets['images'][imageKey] : null;
    final String? text = current?['text'];

    return Scaffold(
      appBar: AppBar(
        title: Text("Yoga Session"),
        backgroundColor: Colors.green[400],
      ),
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            if (imageFile != null)
              Image.asset('assets/images/$imageFile', height: 250)
            else
              Icon(Icons.image_not_supported, size: 100),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                text ?? '',
                textAlign: TextAlign.center,
                style: TextStyle(fontSize: 20),
              ),
            ),
            Text(
              'Step ${currentSegmentIndex + 1} of ${sequence.length}',
              style: TextStyle(fontSize: 16),
            ),
            LinearProgressIndicator(
              value: (currentSegmentIndex + 1) / sequence.length,
              backgroundColor: Colors.grey[300],
              color: Colors.green,
              minHeight: 10,
            ),
            ElevatedButton(
              onPressed: nextPose,
              child: Text('Next Pose'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                foregroundColor: Colors.white,
                padding: EdgeInsets.symmetric(horizontal: 24, vertical: 12),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
